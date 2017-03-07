import React from 'react';
import ReactDom from 'react-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Drawer from 'material-ui/Drawer';
import darkBaseTheme from 'material-ui/styles/baseThemes/darkBaseTheme';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import AppBar from 'material-ui/AppBar';
import injectTapEventPlugin from 'react-tap-event-plugin';
import TextField from 'material-ui/TextField';
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn} from 'material-ui/Table';
import RaisedButton from 'material-ui/RaisedButton';
import { Card, CardActions, CardHeader, CardMedia, CardTitle, CardText } from 'material-ui/Card';
import browserHistory from 'react-router';
import Divider from 'material-ui/Divider';
//import Background from './images/Unbenannt.PNG';

injectTapEventPlugin();

/*var sectionStyle = {
   backgroundImage: `url(${Background})`
};*/
// style={ sectionStyle }

function handleClick(e) {
    e.preventDefault();
    console.log('The link was clicked.');
	browserHistory.push('./Exercise')
  }
	
class Login extends React.Component {
  render() {
    return (
      <div>
        <Header />
        <Content/>
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
	
      <div style={{'margin': '0'}}>
        <MuiThemeProvider muiTheme={getMuiTheme(darkBaseTheme)}>
          <div>
            <AppBar title="Colorado"/>
          </div>
        </MuiThemeProvider>
      </div>
    );
  }
}

class Content extends React.Component {
  render() { 
    return (
	<MuiThemeProvider muiTheme={getMuiTheme(darkBaseTheme)}>
		<div id="content" style={{'marginLeft': '35%', 'marginRight': '35%', 'marginTop': '10%'}}>
      <Card>
        <CardHeader
          title="Login"
        />
        <Divider />
        <CardText>
        <div>
          <TextField
            floatingLabelText="Username"
          />
          <br/>
          <TextField
            floatingLabelText="Password"
            type="password"
          />
          <br/>
          <br/>
          <RaisedButton label="Login" />
          </div>
        </CardText>
        </Card>
		</div>
	</MuiThemeProvider>
    );
  }
}

export default Login;