import React from 'react';
import ReactDom from 'react-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Drawer from 'material-ui/Drawer';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import AppBar from 'material-ui/AppBar';
import injectTapEventPlugin from 'react-tap-event-plugin';
import TextField from 'material-ui/TextField';
import { Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn } from 'material-ui/Table';
import RaisedButton from 'material-ui/RaisedButton';
import { Card, CardActions, CardHeader, CardMedia, CardTitle, CardText } from 'material-ui/Card';
import { browserHistory } from 'react-router';
import Divider from 'material-ui/Divider';
import Avatar from 'material-ui/Avatar';
import { Tabs, Tab } from 'material-ui/Tabs';
import DropDownMenu from 'material-ui/DropDownMenu';
//import Background from './images/Unbenannt.PNG';

injectTapEventPlugin();

var coursesJSON;
const courselist = [];
const courseids = [];

export class Login extends React.Component {
  render() {
    return (
      <div>
        <Header />
        <Content />
      </div>
    );
  }
}

//Navigation and AppBar

class Header extends React.Component {
  constructor() {
    super();
    this.state = {
      open: true
    };
  }

  render() {
    return (

      <div style={{ 'margin': '0' }}>
        <MuiThemeProvider muiTheme={getMuiTheme()}>
          <div>
            <AppBar
              title="Colorado-Login"
              iconElementLeft={<Avatar
                src="images/colorado.jpg"
                size={45}
              />}
              style={{ 'backgroundColor': '#bd051f' }} />
          </div>
        </MuiThemeProvider>
      </div>
    );
  }
}

class Content extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      username: '',
      password: '',
      selectedCourse: 0,
      selectedcourseid: '',
      courselist: []
    };
    this.handleClick = this.handleClick.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleChangePassword = this.handleChangePassword.bind(this);
    this.handleChangeCourse = this.handleChangeCourse.bind(this);
  }

  componentDidMount() {
    $.ajax({
      url: "http://localhost:8181/registration",
      dataType: 'json',
      method: 'GET',
      success: function (courses) {
        coursesJSON = courses;
        if (courselist.length === 0) {
          for (var i = 0; i < coursesJSON.length; i++) {
            courselist.push(<MenuItem value={i} key={i} primaryText={coursesJSON[i].title} />);
            courseids.push(coursesJSON[i].id);
          }
        }
        this.setState({ courselist: courselist });
        this.setState({ selectedcourseid: courseids[0] });
      }.bind(this)
    });
  }


  handleClick(e) {
    e.preventDefault();
    var username = this.state.username;
    var password = this.state.password;
    console.log(username);
    console.log(password);
    var obj = "username=" + username + "&password=" + password;


    fetch('http://localhost:8181', {
      method: 'POST',
      credentials: 'same-origin',
      headers: {
        'Authorization': 'Basic ' + btoa(username + ':' + password),
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    }).then(function (response) {
      return response.json();
    }).then(function (response) {
      if (response === true) {
        browserHistory.push('/docent');
      }
    }).catch(function (err) {
      console.log(err)
    });
  }

  handleChangeCourse(event, index, value) {
    this.setState({ selectedCourse: value });
    this.setState({ selectedcourseid: courseids[value] });
  }

  handleClickPassword(e) {
    e.preventDefault();
    var username = this.state.username;
    var password = this.state.password;
    console.log(username);
    console.log(password);
    var obj = "username=" + username + "&password=" + password;

    fetch('http://localhost:8181', {
      method: 'POST',
      credentials: 'same-origin',
      headers: {
        'Authorization': 'Basic ' + btoa(username + ':' + password),
        'Content-Type': 'application/x-www-form-urlencoded'
      }
    }).then(function (response) {
      return response.json();
    }).then(function (response) {
      if (response === true) {
        browserHistory.push('/docent');
      }
    }).catch(function (err) {
      console.log(err)
    });
  }

  handleChange(event) {
    this.setState({ username: event.target.value });
  }

  handleChangePassword(event) {
    this.setState({ password: event.target.value });
  }
  /*componentWillMount() {
     console.log('Component WILL MOUNT!')
  }*/
  render() {
    return (
      <MuiThemeProvider muiTheme={getMuiTheme()}>
        <div id="content" style={{ 'marginLeft': '35%', 'marginRight': '35%', 'marginTop': '5%' }}>
          <Tabs>
            <Tab label="Login" style={{ 'backgroundColor': '#bd051f' }}>
              <Card>
                <CardHeader className="loginheader">
                  <Avatar
                    src="images/colorado.jpg"
                    size={60}
                    className="coloradologo"
                  />
                </CardHeader>
                />
        <Divider />
                <CardText className="loginbody">
                  <div >
                    <TextField
                      className="logintext"
                      floatingLabelText="Username"
                      type='text'
                      id="username"
                      name="username"
                      value={this.state.username}
                      onChange={this.handleChange}
                      floatingLabelStyle={{ 'color': '#000000' }}
                      underlineFocusStyle={{ 'borderColor': '#000000' }}
                      floatingLabelFocusStyle={{ 'color': '#000000' }}
                      errorStyle={{ 'color': '#bd051f' }}
                      style={{ 'width': '98%', 'fontSize': '150%' }}
                    />
                    <br />
                    <br />
                    <TextField
                      floatingLabelText="Password"
                      type="password"
                      id="password"
                      name="password"
                      onChange={this.handleChangePassword}
                      floatingLabelStyle={{ 'color': '#000000' }}
                      underlineFocusStyle={{ 'borderColor': '#000000' }}
                      floatingLabelFocusStyle={{ 'color': '#000000' }}
                      errorStyle={{ 'color': '#bd051f' }}
                      style={{ 'width': '98%', 'fontSize': '150%' }}
                    />
                    <br />
                    <br />

                  </div>
                </CardText>
                <CardActions className="footer">
                  <RaisedButton
                    label="Login"
                    onClick={this.handleClick}
                    backgroundColor="#bd051f"
                    labelColor="#FFFFFF" />
                </CardActions>
              </Card>
            </Tab>
            <Tab label="Registration" style={{ 'backgroundColor': '#bd051f' }}>
              <Card>
                <CardHeader className="loginheader">
                  <Avatar
                    src="images/colorado.jpg"
                    size={60}
                    className="coloradologo"
                  />
                </CardHeader>
                />
        <Divider />
                <CardText className="loginbody">
                  <div >
                    <TextField
                      className="logintext"
                      floatingLabelText="First Name"
                      type='text'
                      id="firstName"
                      name="firstName"
                      value={this.state.username}
                      onChange={this.handleChange}
                      floatingLabelStyle={{ 'color': '#000000' }}
                      underlineFocusStyle={{ 'borderColor': '#000000' }}
                      floatingLabelFocusStyle={{ 'color': '#000000' }}
                      errorStyle={{ 'color': '#bd051f' }}
                      style={{ 'width': '98%', 'fontSize': '150%' }}
                    />
                    <br />
                    <br />
                    <TextField
                      className="logintext"
                      floatingLabelText="Last Name"
                      type='text'
                      id="lastName"
                      name="lastName"
                      value={this.state.username}
                      onChange={this.handleChange}
                      floatingLabelStyle={{ 'color': '#000000' }}
                      underlineFocusStyle={{ 'borderColor': '#000000' }}
                      floatingLabelFocusStyle={{ 'color': '#000000' }}
                      errorStyle={{ 'color': '#bd051f' }}
                      style={{ 'width': '98%', 'fontSize': '150%' }}
                    />
                    <br />
                    <br />
                    <TextField
                      className="logintext"
                      floatingLabelText="Username"
                      type='text'
                      id="usernameReg"
                      name="usernameReg"
                      value={this.state.username}
                      onChange={this.handleChange}
                      floatingLabelStyle={{ 'color': '#000000' }}
                      underlineFocusStyle={{ 'borderColor': '#000000' }}
                      floatingLabelFocusStyle={{ 'color': '#000000' }}
                      errorStyle={{ 'color': '#bd051f' }}
                      style={{ 'width': '98%', 'fontSize': '150%' }}
                    />
                    <br />
                    <br />
                    <TextField
                      floatingLabelText="Password"
                      type="password"
                      id="passwordReg"
                      name="password"
                      onChange={this.handleChangePassword}
                      floatingLabelStyle={{ 'color': '#000000' }}
                      underlineFocusStyle={{ 'borderColor': '#000000' }}
                      floatingLabelFocusStyle={{ 'color': '#000000' }}
                      errorStyle={{ 'color': '#bd051f' }}
                      style={{ 'width': '98%', 'fontSize': '150%' }}
                    />
                    <br />
                    <br />
                    <TextField
                      floatingLabelText="Repeat Password"
                      type="password"
                      id="passwordReg"
                      name="password"
                      onChange={this.handleChangePassword}
                      floatingLabelStyle={{ 'color': '#000000' }}
                      underlineFocusStyle={{ 'borderColor': '#000000' }}
                      floatingLabelFocusStyle={{ 'color': '#000000' }}
                      errorStyle={{ 'color': '#bd051f' }}
                      style={{ 'width': '98%', 'fontSize': '150%' }}
                    />
                    <br />
                    <br />
                    <DropDownMenu value={this.state.selectedCourse} onChange={this.handleChangeCourse}>
                      {this.state.courselist}
                    </DropDownMenu>

                  </div>
                </CardText>
                <CardActions className="footer">
                  <RaisedButton
                    label="Registration"
                    onClick={this.handleClickReg}
                    backgroundColor="#bd051f"
                    labelColor="#FFFFFF" />
                </CardActions>
              </Card>
            </Tab>
          </Tabs>
        </div>
      </MuiThemeProvider>
    );
  }
}

export default Login;