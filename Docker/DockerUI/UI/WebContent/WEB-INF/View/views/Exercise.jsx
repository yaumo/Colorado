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
import Paper from 'material-ui/Paper';
import DropDownMenu from 'material-ui/DropDownMenu';
import EditorAce from './components/EditorAce.jsx';
import TextField from 'material-ui/TextField';
import Dialog from 'material-ui/Dialog';
import Drawer from 'material-ui/Drawer';
import AppBar from 'material-ui/AppBar';
import { white, red500, black, green } from 'material-ui/styles/colors';
import ActionPerson from 'material-ui/svg-icons/action/account-circle';
import ActionCheck from 'material-ui/svg-icons/action/check-circle';
import IconButton from 'material-ui/IconButton';
import IconMenu from 'material-ui/IconMenu';
import { browserHistory } from 'react-router';
import Avatar from 'material-ui/Avatar';


var courseJSON;
var solutionJSON;
var currentExerciseJSON;
const lecturelist = [];
const lectureids = [];
const exerciseslist = [];
const exerciseids = [];
var data;
const personStyles = {
    marginRight: 24,
    marginTop: 12,
};

const iconStyles = {
    marginRight: 12,
};
export class Exercise extends React.Component {
    constructor() {
        super();
        this.state = {
            open: true,
            opendialog: false,
            value: 1,
            java: 0,
            dialog: '',
            solution: '',
            exerciseJSON: '',
            language: '',
            languageformatted: '',
            title: '',
            description: '',
            youtube: '',
            dropdown: 0,
            selectedlectureid: '',
            selectedexerciseid: ''
        };
        this.handleChange = this.handleChange.bind(this);
        this.handleCheck = this.handleCheck.bind(this);
        this.handleOpenDialog = this.handleOpenDialog.bind(this);
        this.handleCloseDialog = this.handleCloseDialog.bind(this);
        this.handleChangeSolution = this.handleChangeSolution.bind(this);
        this.setState = this.setState.bind(this);
        this.onClick = this.onClick.bind(this);
    }


    componentDidMount() {
        $.ajax({
            url: "https://192.168.99.100:8081/api/course",
            ddataType: 'json',
            method: 'GET',
            xhrFields: {
                withCredentials: true
            },
            success: function (course) {
                courseJSON = course;
                if (lecturelist.length === 0) {
                    for (var i = 0; i < courseJSON.lectures.length; i++) {
                        lecturelist.push(<MenuItem value={i} key={i} primaryText={courseJSON.lectures[i].title} />);
                        lectureids.push(courseJSON.lectures[i].id);
                    }
                    this.setState({ selectedlectureid: lectureids[0] });
                }
                if (exerciseslist.length === 0) {
                    for (var j = 0; j < courseJSON.lectures[0].exercises.length; j++) {
                        exerciseslist.push(<MenuItem value={j} key={j} primaryText={courseJSON.lectures[0].exercises[j].title} onClick={this.handleClickOnMenu.bind(this, j)} />);
                        exerciseids.push(courseJSON.lectures[0].exercises[j].id);
                    }
                }
                this.setState({
                    selectedexerciseid: exerciseids[0]
                });
                
                //handleClickOnMenu(0);
            }.bind(this),
            error: function (error) {

            }.bind(this)
        });
    }

    handleClickOnMenu(value) {
        var exerciseID = exerciseids[value];
        //exerciseID auslesen!

        var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
            escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
            meta = { // table of character substitutions
                '\\b': '\b',
                '\\t': '\t',
                '\\n': '\n',
                '\\f': '\f',
                '\\r': '\r',
                '\\"': '"',
                '\\\\': '\\'
            };

        function json_quote(string) {
            escapable.lastIndex = 0;
            return escapable.test(string) ? '' + string.replace(escapable, function (a) {
                var c = meta[a];
                return typeof c === 'string' ? c :
                    '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
            }) + '' : '' + string + '';
        }

        $.ajax({
            url: "https://192.168.99.100:8081/api/exercise",
            dataType: 'json',
            method: 'GET',
            xhrFields: {
                withCredentials: true
            },
            data: {
                "exeId": exerciseID
            },
            success: function (currentExercise) {
                currentExerciseJSON = currentExercise;
                if(currentExerciseJSON.language==='Java'){
                    this.setState({languageformatted: 'java'});
                }
                else if(currentExerciseJSON.language==='JavaScript'){
                    this.setState({languageformatted: 'javascript'});
                }
                this.setState({
                    title: currentExerciseJSON.title,
                    description: currentExerciseJSON.description,
                    language: currentExerciseJSON.language,
                    youtube: currentExerciseJSON.videolink,
                    exerciseJSON: currentExerciseJSON
                });
                $.ajax({
                    url: "https://192.168.99.100:8081/api/solution",
                    dataType: 'json',
                    method: 'GET',
                    xhrFields: {
                        withCredentials: true
                    },
                    data: {
                        "exeId": currentExerciseJSON.id
                    },
                    success: function (solution) {
                        solutionJSON = solution;
                        this.setState({
                            solution: solutionJSON.code
                        });
                    }.bind(this),
                    error: function (error) {
                        this.setState({
                            //opendialog: true,
                            //dialog: "An error has occoured. Please make sure that you are logged in ."
                        });
                    }.bind(this)
                });
                //this.props.setExerciseJSON('2');
                //Daten aus der Component müssen in Component Exercise 
            }.bind(this),
            error: function (error) {

            }.bind(this)
        });
    }


    handleChange(event, index, value) {
        var count = exerciseslist.length;
        for (var i = 0; i < count; i++) {
            exerciseslist.pop();
            exerciseids.pop();
        }
        for (var j = 0; j < courseJSON.lectures[value].exercises.length; j++) {
            exerciseslist.push(<MenuItem value={j} key={j} primaryText={courseJSON.lectures[value].exercises[j].title} onClick={this.handleClickOnMenu.bind(this, j)} />);
            exerciseids.push(courseJSON.lectures[value].exercises[j].id);
        }

        this.setState({ dropdown: value });
        this.setState({ selectedlectureid: lectureids[value] });
    }

    onClick(e) {
        e.preventDefault();
        if (e.target.textContent == "Settings") {
            browserHistory.push('/user');
        }
        else {
            $.ajax({
                url: "https://192.168.99.100:8081/api/logout",
                dataType: 'json',
                method: 'POST',
                xhrFields: {
                    withCredentials: true
                },
                success: function (response) {
                    browserHistory.push('/');
                }.bind(this),
                error: function (error) {
                    browserHistory.push('/');
                }
            });
        }
    }
    handleOpenDialog(event, index, value) {
        this.setState({ opendialog: true });
    }
    handleCloseDialog(event, index, value) {
        this.setState({ opendialog: false });
    }



    handleCheck(event, index, value) {
        var solutionCode = this.state.solution;
        var exerciseJSON = this.state.exerciseJSON;
        var id = exerciseJSON.id;
        var solId = solutionJSON.id;

        $.ajax({
            url: "https://192.168.99.100:8081/api/solution",
            dataType: 'json',
            method: 'PATCH',
            xhrFields: {
                withCredentials: true
            },
            data: JSON.stringify({
                "id": solId,
                "solution": solutionCode
            }),
            success: function (response) {
                this.setState({
                    opendialog: true,
                    dialog: "Your solution is correct"
                    //response.message.toString()
                });
                //setStates to ''
            }.bind(this),
            error: function (error) {
                this.setState({
                    opendialog: true,
                    dialog: "Your solution is not correct"
                });
            }.bind(this)
        });
    }

    handleChangeSolution(event) {
        this.setState({ solution: event });
    }

    render() {
        return (
            <div>
                <div style={{ 'margin': '0' }}>
                <MuiThemeProvider muiTheme={getMuiTheme()}>
                    <div>
                        <AppBar
                            title="Colorado"
                            style={{ 'position': 'fixed', 'backgroundColor': '#bd051f', 'opacity': '0.9' }}
                            iconElementLeft={<Avatar
                                src="images/colorado.jpg"
                                size={45}
                            />}
                            iconElementRight={
                                <IconMenu
                                    iconButtonElement={<IconButton>
                                        <ActionPerson style={personStyles} color={white} hoverColor={black} />
                                    </IconButton>}
                                    targetOrigin={{ horizontal: 'right', vertical: 'top' }}
                                    anchorOrigin={{ horizontal: 'right', vertical: 'top' }}
                                >
                                    <MenuItem primaryText="Settings" onClick={this.onClick} />
                                    <MenuItem primaryText="Sign out" onClick={this.onClick} />
                                </IconMenu>
                            }
                        />

                        <Drawer
                            id="drawer"
                            docked={true}
                            onItemTouchTap={this.handleClickOnMenu}
                            open={this.state.open}
                            onRequestChange={(open) => this.setState({ open })}
                            containerStyle={{ 'position': 'fixed', 'margin': '0', 'top': '64px', 'height': 'calc(100% - 64px)', 'backgroundColor': '#959595', 'background': '-webkit-linear-gradient(#bbbbbb, #959595)' }}
                        >
                            <DropDownMenu value={this.state.dropdown} onChange={this.handleChange} >
                                {lecturelist}
                            </DropDownMenu>
                            <Divider />
                                {exerciseslist}

                        </Drawer>
                    </div>
                </MuiThemeProvider>
            </div>


                <div id="content" className="content">
                    <MuiThemeProvider muiTheme={getMuiTheme()}>
                        <div>
                            <div id="exercise">
                                <Card>
                                    <CardHeader
                                        title={this.state.title}
                                        className="loginheader"
                                        titleColor="white"
                                    />
                                    <Divider />
                                    <CardText className="loginbody">
                                        <div>
                                            {this.state.description}
                                        </div>
                                        <br />
                                        <div>
                                            <iframe width="560" height="315" src={this.state.youtube} frameBorder="0" allowFullScreen></iframe>
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
                                            floatingLabelText={this.state.language}
                                            fullWidth={false}
                                            disabled={true}
                                            underlineShow={false}
                                            floatingLabelStyle={{ 'color': '#000000' }}
                                        />

                                        <Paper zDepth={4}>
                                            <EditorAce value={this.state.solution} handleChange={this.handleChangeSolution} mode={this.state.languageformatted} />
                                        </Paper>
                                    </CardText>
                                    <CardActions className="footer">
                                        <RaisedButton label="Check"
                                            backgroundColor="#bd051f"
                                            labelColor="#FFFFFF"
                                            onClick={this.handleCheck} />
                                        <Dialog
                                            title="Dialog With Actions"
                                            modal={false}
                                            open={this.state.opendialog}
                                            onRequestClose={this.handleCloseDialog}
                                        >
                                            {this.state.dialog}
									</Dialog>
                                    </CardActions>
                                </Card>
                            </div>
                        </div>
                    </MuiThemeProvider>
                </div>
            </div>
        );
    }
}

export default Exercise;