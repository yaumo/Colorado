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
import MenuItem from 'material-ui/MenuItem';
import Dialog from 'material-ui/Dialog';
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
      courselist: [],
      dialog: '',
      opendialog: false
    };
    this.handleClick = this.handleClick.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleClickReg = this.handleClickReg.bind(this);
    this.handleChangePassword = this.handleChangePassword.bind(this);
    this.handleChangeCourse = this.handleChangeCourse.bind(this);
    this.handleCloseDialog = this.handleCloseDialog.bind(this);
    this.handleOpenDialog = this.handleOpenDialog.bind(this);
  }

  componentDidMount() {
    $.ajax({
      url: "https://192.168.99.100:8081/registration",
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


    fetch('https://192.168.99.100:8081', {
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
      else if (response === false) {
        browserHistory.push('/exercise');
      }
    }).catch(function (err) {
      console.log(err)
    });
  }

  handleChangeCourse(event, index, value) {
    this.setState({ selectedCourse: value });
    this.setState({ selectedcourseid: courseids[value] });
  }

  handleOpenDialog(event, index, value) {
    this.setState({ opendialog: true });
  }
  handleCloseDialog(event, index, value) {
    this.setState({ opendialog: false });
  }

  handleClickReg(e) {
    e.preventDefault();
    var courseId = this.state.selectedcourseid;
    var firstName = $("#firstName")[0].value.toString();
    var lastName = $("#lastName")[0].value.toString();
    var username = $("#usernameReg")[0].value.toString();
    var password = $("#passwordReg")[0].value.toString();
    var passwordRepeat = $("#passwordRegRepeat")[0].value.toString();

    if ((firstName || lastName) === "") {
      this.setState({
        opendialog: true,
        dialog: "Please enter your name.",
      });
    }
    else if (username === "") {
      this.setState({
        opendialog: true,
        dialog: "Please enter a username.",
      });
    }
    else if ((password || passwordRepeat) === "") {
      this.setState({
        opendialog: true,
        dialog: "Please enter your password and repeat it.",
      });
    }
    else if (password.length < 6) {
      this.setState({
        opendialog: true,
        dialog: "Your password must have at least 6 characters. Please try again."
      });
    }
    else if (password !== passwordRepeat) {
      this.setState({
        opendialog: true,
        dialog: "Passwords do not match. Please try again."
      });
    }
    else {

      $.ajax({
        url: "https://192.168.99.100:8081/registration",
        dataType: 'json',
        method: 'POST',
        data: JSON.stringify({
          "firstName": firstName,
          "lastName": lastName,
          "username": username,
          "password": password,
          "course": {"id": courseId}
        }),
        success: function (response) {
          if (response == false) {
            browserHistory.push('/exercise');
          }
        }.bind(this),
        error: function (error) {
          this.setState({
            opendialog: true,
            dialog: "An error occurred. Please try again!"
          });
        }.bind(this)
      });
    }
  }

  handleChange(event) {
    this.setState({ username: event.target.value });
  }

  handleChangePassword(event) {
    this.setState({ password: event.target.value });
  }

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
                      id="passwordRegRepeat"
                      name="passwordRegRepeat"
                      floatingLabelStyle={{ 'color': '#000000' }}
                      underlineFocusStyle={{ 'borderColor': '#000000' }}
                      floatingLabelFocusStyle={{ 'color': '#000000' }}
                      errorStyle={{ 'color': '#bd051f' }}
                      style={{ 'width': '98%', 'fontSize': '150%' }}
                    />
                    <br />
                    <br />
                    <DropDownMenu value={this.state.selectedCourse} onChange={this.handleChangeCourse} style={{ 'width': '98%', 'fontSize': '150%' }}>
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
                <Dialog
                  title="Information"
                  modal={false}
                  open={this.state.opendialog}
                  onRequestClose={this.handleCloseDialog}
                >
                  {this.state.dialog}
                </Dialog>
              </Card>
            </Tab>
          </Tabs>
        </div>
      </MuiThemeProvider>
    );
  }
}

export default Login;