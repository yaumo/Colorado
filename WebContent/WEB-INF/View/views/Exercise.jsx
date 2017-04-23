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
import Paper from 'material-ui/Paper';
import DropDownMenu from 'material-ui/DropDownMenu';
import EditorAce from './components/EditorAce.jsx';
import TextField from 'material-ui/TextField';
import Dialog from 'material-ui/Dialog';


var courseJSON;

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
    constructor() {
        super();
        this.state = {
            open: true,
			opendialog: false,
            value: 1,
            java: 0,
			solution: ''
        };
		this.handleChange = this.handleChange.bind(this);
		this.handleOpenDialog = this.handleOpenDialog.bind(this);
		this.handleCloseDialog = this.handleCloseDialog.bind(this);
		this.handleChangeSolution = this.handleChangeSolution.bind(this);
    }		
		handleChange(event, index, value) {
		this.setState({java: value});
		}
		handleOpenDialog(event, index, value) {
			this.setState({opendialog: true});
		}
		handleCloseDialog(event, index, value) {
			this.setState({opendialog: false});
		}
        
    handleChange(event, index, value) {
        this.setState({ java: value });
    }

    handleCheck(event, index, value) {
        var solutionCode = this.getState().solution;


		$.ajax({
                url: "http://localhost:8181/solution",
                dataType: 'json',
                method: 'POST',
                xhrFields: {
                    withCredentials: true
                },
                data: JSON.stringify({
                    "solution": solution
                }),
                success: function (response) {
                    this.setState({
                        opendialog: true,
                        dialog: response.message.toString()
                    });
                    //setStates to ''
                }.bind(this),
                error: function (error) {
                    this.setState({
                        opendialog: true,
                        dialog: "An error has occoured. Please make sure that you are logged in ."
                    });
                }.bind(this)
            });
	}

	handleChangeSolution(event){
		this.setState({solution: event});
	}
    render() {
        return (
            <div id="content" className="content">
                <MuiThemeProvider muiTheme={getMuiTheme()}>
                    <div>
                        <div id="exercise">
                            <Card>
                                <CardHeader
                                    title="Exercise 1"
                                    className="loginheader"
                                    titleColor="white"
                                />
                                <Divider />
                                <CardText className="loginbody">
                                    <div>
                                        Lorem ipsum dolor sit amet, consectetur adipiscing elit.
                                    Donec mattis pretium massa. Aliquam erat volutpat. Nulla facilisi.
                                    Donec vulputate interdum sollicitudin. Nunc lacinia auctor quam sed pellentesque.
                                    Aliquam dui mauris, mattis quis lacus id, pellentesque lobortis odio.
                                </div>
                                    <br />
                                    <div>
                                        <iframe width="560" height="315" src="https://www.youtube.com/embed/Ztc6_elMg60" frameBorder="0" allowFullScreen></iframe>
                                    </div>
                                </CardText>
                                <CardActions className="footer">
                                </CardActions>
                            </Card>
                        </div>
                        <br />
                        <div id="solution">
                            <Card>
                                <CardHeader
                                    title="Solution"
                                    className="loginheader"
                                    titleColor="white"
                                >
                                    <div style={{ textAlign: "right" }}>In Progress</div>
                                </CardHeader>
                                <Divider />
                                <CardText className="loginbody">
									<TextField
										floatingLabelText="JavaScript"
										fullWidth={false}
										disabled={true}
										underlineShow={false}
										floatingLabelStyle={{'color':'#000000'}}
									/>
                                   
                                    <Paper zDepth={4}>
                                        <EditorAce value={this.state.solution} handleChange={this.handleChangeSolution}/>
                                    </Paper>
                                </CardText>
                                <CardActions className="footer">
                                    <RaisedButton label="Check"
									backgroundColor="#bd051f"
									labelColor="#FFFFFF"
									onClick={this.onCheck}/>
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
                    </div>
                </MuiThemeProvider>
            </div>
        );
    }
}

export default Exercise;