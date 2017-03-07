import React from 'react';
import ReactDom from 'react-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Drawer from 'material-ui/Drawer';
import MenuItem from 'material-ui/MenuItem';
import darkBaseTheme from 'material-ui/styles/baseThemes/darkBaseTheme';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import AppBar from 'material-ui/AppBar';
import injectTapEventPlugin from 'react-tap-event-plugin';


class App extends React.Component {
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
      <div style={{'margin': '0'}}>
        <MuiThemeProvider muiTheme={getMuiTheme(darkBaseTheme)}>
          <div>
            <AppBar title="Tutorial"/>
            <Drawer
              id="drawer"
              docked={true}
              open={this.state.open}
              onRequestChange={(open) => this.setState({ open })}
              containerStyle={{'position': 'absolute', 'margin': '0', 'top': '64px', height: 'calc(100% - 64px)'}}
              >
              <MenuItem>Lektion 1</MenuItem>
              <MenuItem>Lektion 2</MenuItem>
            </Drawer>
          </div>
        </MuiThemeProvider>
      </div>
    );
  }
}

class Content extends React.Component {
  render() {
    return (
      <div id="content" style={{'marginLeft': '256px'}}>
        <h2>Content</h2>
        <p>The content text!!!</p>
      </div>
    );
  }
}

export default App;