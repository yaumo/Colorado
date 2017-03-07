import React from 'react';
import ReactDom from 'react-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Drawer from 'material-ui/Drawer';
import MenuItem from 'material-ui/MenuItem';
import darkBaseTheme from 'material-ui/styles/baseThemes/darkBaseTheme';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import AppBar from 'material-ui/AppBar';
import injectTapEventPlugin from 'react-tap-event-plugin';
import Divider from 'material-ui/Divider';
import RaisedButton from 'material-ui/RaisedButton';
import { Card, CardActions, CardHeader, CardMedia, CardTitle, CardText } from 'material-ui/Card';
import DropDownMenu from 'material-ui/DropDownMenu';
import CodeMirror from 'react-codemirror';
import {Toolbar, ToolbarGroup, ToolbarSeparator, ToolbarTitle} from 'material-ui/Toolbar';


//Codemirror with webpack

class Exercise extends React.Component {
    render() {
        return (
            <div>
                <Header />
                <Content />
            </div>
        );
    }
}

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
                <MuiThemeProvider muiTheme={getMuiTheme(darkBaseTheme)}>
                    <div>
                        <AppBar title="Colorado" style={{ 'position': 'fixed'}} />

                        <Drawer
                            id="drawer"
                            docked={true}
                            open={this.state.open}
                            onRequestChange={(open) => this.setState({ open })}
                            containerStyle={{ 'position': 'fixed', 'margin': '0', 'top': '64px', height: 'calc(100% - 64px)' }}
                            className= ""
                            >
                            <DropDownMenu  value={this.value}>
                                <MenuItem value={1} primaryText="Webprogrammierung" />
                                <MenuItem value={2} primaryText="Datenbanken" />
                                <MenuItem value={3} primaryText="Programmieren 1" />
                            </DropDownMenu>
                            <Divider />
                            <MenuItem>Exercise 1</MenuItem>
                            <Divider />
                            <MenuItem>Exercise 2</MenuItem>
                            <Divider />
                            <RaisedButton label="+" />
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
            <div id="content" className="content">
                <MuiThemeProvider muiTheme={getMuiTheme(darkBaseTheme)}>
                    <div>
                        <div id="exercise">
                            <Card>
                                <CardHeader
                                    title="Exercise 1"
                                    />
                                <Divider />
                                <CardText>
                                <div>
                                    Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                                    Donec mattis pretium massa. Aliquam erat volutpat. Nulla facilisi.
                                    Donec vulputate interdum sollicitudin. Nunc lacinia auctor quam sed pellentesque.
                                    Aliquam dui mauris, mattis quis lacus id, pellentesque lobortis odio.
                                </div>
                                 <br/>
                                <div>
                                    <iframe width="560" height="315" src="https://www.youtube.com/embed/Ztc6_elMg60" frameBorder="0" allowFullScreen></iframe>
                                </div>
                                </CardText>
                            </Card>
                        </div>
                        <br />
                        <div id="solution">
                            <Card>
                                <CardHeader
                                    title="Solution"
                                    />
                                    <Divider />
                                <CardText>
                                    <div>
                                        <CodeMirror ref="editor" />
                                    </div>
                                    <br/>
                                    <RaisedButton label="Check" />
                                </CardText>
                            </Card>
                        </div>
                    </div>
                </MuiThemeProvider>
            </div>
        );
    }
}

export default Exercise;