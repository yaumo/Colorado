import React from 'react';
import ReactDom from 'react-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import MenuItem from 'material-ui/MenuItem';
import darkBaseTheme from 'material-ui/styles/baseThemes/darkBaseTheme';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import injectTapEventPlugin from 'react-tap-event-plugin';
import Divider from 'material-ui/Divider';
import RaisedButton from 'material-ui/RaisedButton';
import { Card, CardActions, CardHeader, CardMedia, CardTitle, CardText } from 'material-ui/Card';
import CodeMirror from 'react-codemirror';
import NavBar from './components/NavBar.jsx';
import Solution from './components/Solution.jsx';
import Paper from 'material-ui/Paper';


export class Exercise extends React.Component {
    render() {
        return (
            <div>
                <NavBar />
                <Content />
            </div>
        );
    }
}

class Content extends React.Component {
    render() {
        return (
            <div id="content" className="content">
                <MuiThemeProvider muiTheme={getMuiTheme()}>
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
                                    <Paper zDepth={4}>
                                    <Solution/>
                                    </Paper> 
                                    <br />
                                    <RaisedButton label="Check"/>
                                    
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