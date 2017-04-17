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
    this.state = { value: '',opendialog: false };
    this.handleClick = this.handleClick.bind(this);
    this.handleChange = this.handleChange.bind(this);
	this.handleOpenDialog = this.handleOpenDialog.bind(this);
	this.handleCloseDialog = this.handleCloseDialog.bind(this);
  }
  handleClick(e) {
    alert("Changed Password!");
  }
  
  handleChange(event) {
    this.setState({ value: event.target.value });
  }
  handleOpenDialog(event, index, value) {
	this.setState({opendialog: true});
  }
  handleCloseDialog(event, index, value) {
	this.setState({opendialog: false});
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
                  floatingLabelText="First Name"
                  type='text'
                  id="firstName"
                  disabled={true}
                  fullWidth={true}
                  defaultValue="Nico"
                />
                <br />
                <TextField
                  floatingLabelText="Last Name"
                  type='text'
                  id="lastName"
                  disabled={true}
                  defaultValue="Himmelein"
                  fullWidth={true}
                />
                <br />
                <TextField
                  floatingLabelText="eMail"
                  type='text'
                  id="lastName"
                  disabled={true}
                  fullWidth={true}
                  defaultValue="s12345@student.dhbw-mannheim.de"
                />
                <br />
                <TextField
                  floatingLabelText="Old Password"
                  type="password"
                  id="oldPassword"
                  underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                  floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                  fullWidth={true}
                />
                <br />
                <TextField
                  floatingLabelText="New Password"
                  type="password"
                  id="newPassword"
                  underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                  floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                  fullWidth={true}
                />
                <br />
                <TextField
                  floatingLabelText="Repeat New Password"
                  type="password"
                  id="newPassword"
                  underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                  floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                  fullWidth={true}
                />

              </div>
            </CardText>
            <CardActions className="footer">
              <RaisedButton 
			  label="Confirm" 
			  onClick={this.handleClick}
			  backgroundColor="#bd051f"
			  labelColor="#FFFFFF"/>
			<Dialog
			title="Dialog With Actions"
			modal={false}
			open={this.state.opendialog}
			onRequestClose={this.handleCloseDialog}
			>
				The actions in this window were passed in as an array of React objects.
			</Dialog>			  
            </CardActions>
          </Card>
        </div>
      </MuiThemeProvider>
    );
  }
}

export default User;