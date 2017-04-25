import React from 'react';
import ReactDom from 'react-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Drawer from 'material-ui/Drawer';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import AppBar from 'material-ui/AppBar';
import TextField from 'material-ui/TextField';
import { Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn } from 'material-ui/Table';
import RaisedButton from 'material-ui/RaisedButton';
import { Card, CardActions, CardHeader, CardMedia, CardTitle, CardText } from 'material-ui/Card';
import { browserHistory } from 'react-router';
import Divider from 'material-ui/Divider';
import Avatar from 'material-ui/Avatar';
import IconButton from 'material-ui/IconButton';
import NavigationBack from 'material-ui/svg-icons/navigation/arrow-back';
import Dialog from 'material-ui/Dialog';



var currentUserJSON;
var dialog = [];

export class User extends React.Component {
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

  onBack() {
    browserHistory.goBack();
  }

  render() {
    return (

      <div style={{ 'margin': '0' }}>
        <MuiThemeProvider muiTheme={getMuiTheme()}>
          <div>
            <AppBar title="Colorado" style={{ 'backgroundColor': '#bd051f', 'opacity': '0.9' }}
              iconElementLeft={<IconButton><NavigationBack onClick={this.onBack} /></IconButton>}
            />
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
      value: '',
      dialog: '',
      opendialog: false,
      firstname: '',
      lastname: '',
      oldPassword: '',
      newPassword: '',
      repeatPassword: '',
      username: '',
      userId: ''
    };
    this.handleClick = this.handleClick.bind(this);
    this.handleChange = this.handleChange.bind(this);
    this.handleChangeOldPassword = this.handleChangeOldPassword.bind(this);
    this.handleChangeNewPassword = this.handleChangeNewPassword.bind(this);
    this.handleChangeRepeatPassword = this.handleChangeRepeatPassword.bind(this);
    this.handleOpenDialog = this.handleOpenDialog.bind(this);
    this.handleCloseDialog = this.handleCloseDialog.bind(this);
  }

  componentDidMount() {
    $.ajax({
      url: "http://localhost:8181/user",
      dataType: 'json',
      method: 'GET',
      xhrFields: {
                withCredentials: true
            },
      success: function (currentUser) {
        currentUserJSON = currentUser;
        this.setState({
          userId: currentUserJSON.id,
          firstname: currentUserJSON.firstName,
          lastname: currentUserJSON.lastName,
          username: currentUserJSON.username
        })
      }.bind(this),
      error: function (error) {
        alert(error.responseText);
      }
    });
  }

  handleClick(e) {
    if (this.state.newPassword.length < 6) {
      this.setState({
        opendialog: true,
        dialog: "Your new password must have at least 6 characters. Please try again.",
        newPassword: '',
        repeatPassword: ''
      });
    }
    else if (this.state.oldPassword === this.state.newPassword) {
      this.setState({
        opendialog: true,
        dialog: "New password can not be your old password. Please try another one.",
        newPassword: '',
        repeatPassword: ''
      });
    }
    else if (this.state.newPassword !== this.state.repeatPassword) {
      this.setState({
        opendialog: true,
        dialog: "Passwords do not match. Please try again.",
        newPassword: '',
        repeatPassword: ''
      });
    }
    else {
      $.ajax({
        url: "http://localhost:8181/updatePassword",
        dataType: 'json',
        method: 'POST',
        xhrFields: {
                withCredentials: true
            },
        data: JSON.stringify({
          "oldpw": this.state.oldPassword,
          "newpw": this.state.newPassword
        }),
        success: function (response) {
          if (response === true) {
            this.setState({
              opendialog: true,
              dialog: "Password successfully changed",
              newPassword: '',
              repeatPassword: '',
              oldPassword: ''
            });
          }
          else {
            this.setState({
              opendialog: true,
              dialog: "Your old password is incorrect. Please try again.",
              oldPassword: ''
            });
          }
        }.bind(this),
        error: function (error) {
          this.setState({
            opendialog: true,
            dialog: "Your old password is incorrect. Please try again.",
            oldPassword: ''
          });
        }.bind(this)
      });
    }

  }

  handleChange(event) {
    this.setState({ value: event.target.value });
  }
  handleChangeOldPassword(event) {
    this.setState({ oldPassword: event.target.value });
  }
  handleChangeNewPassword(event) {
    this.setState({ newPassword: event.target.value });
  }
  handleChangeRepeatPassword(event) {
    this.setState({ repeatPassword: event.target.value });
  }
  handleOpenDialog(event, index, value) {
    this.setState({ opendialog: true });
  }
  handleCloseDialog(event, index, value) {
    this.setState({ opendialog: false });
  }

  render() {
    return (
      <MuiThemeProvider muiTheme={getMuiTheme()}>
        <div id="content" style={{ 'marginLeft': '35%', 'marginRight': '35%', 'marginTop': '10%' }}>
          <Card>
            <CardHeader className="loginheader">
              <Avatar size={60} src="images/colorado.jpg" className="coloradologo" />
            </CardHeader>
            <Divider />
            <CardText className="loginbody">
              <div>
                <TextField
                  floatingLabelText="userId"
                  type='text'
                  id="userId"
                  disabled={true}
                  fullWidth={true}
                  value={this.state.userId}
                />
                <TextField
                  floatingLabelText="First Name"
                  type='text'
                  id="firstName"
                  disabled={true}
                  fullWidth={true}
                  value={this.state.firstname}
                />
                <br />
                <TextField
                  floatingLabelText="Last Name"
                  type='text'
                  id="lastName"
                  disabled={true}
                  fullWidth={true}
                  value={this.state.lastname}
                />
                <br />
                <TextField
                  floatingLabelText="username"
                  type='text'
                  id="username"
                  disabled={true}
                  fullWidth={true}
                  value={this.state.username}
                />
                <br />
                <TextField
                  floatingLabelText="Old Password"
                  type="password"
                  id="oldPassword"
                  underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                  floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                  fullWidth={true}
                  value={this.state.oldPassword}
                  onChange={this.handleChangeOldPassword}
                />
                <br />
                <TextField
                  floatingLabelText="New Password"
                  type="password"
                  id="newPassword"
                  underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                  floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                  fullWidth={true}
                  value={this.state.newPassword}
                  onChange={this.handleChangeNewPassword}
                />
                <br />
                <TextField
                  floatingLabelText="Repeat New Password"
                  type="password"
                  id="repeatPassword"
                  underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                  floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                  fullWidth={true}
                  value={this.state.repeatPassword}
                  onChange={this.handleChangeRepeatPassword}
                />

              </div>
            </CardText>
            <CardActions className="footer">
              <RaisedButton
                label="Confirm"
                onClick={this.handleClick}
                backgroundColor="#bd051f"
                labelColor="#FFFFFF" />
              <Dialog
                title="Information"
                modal={false}
                open={this.state.opendialog}
                onRequestClose={this.handleCloseDialog}
              >
                {this.state.dialog}
              </Dialog>
            </CardActions>
          </Card>
        </div>
      </MuiThemeProvider>
    );
  }
}

export default User;