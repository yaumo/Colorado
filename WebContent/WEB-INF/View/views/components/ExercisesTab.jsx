import React from 'react';
import ReactDom from 'react-dom';
import { Card, CardActions, CardHeader, CardMedia, CardTitle, CardText } from 'material-ui/Card';
import Divider from 'material-ui/Divider';
import Solution from './Solution.jsx';
import TextField from 'material-ui/TextField';
import DatePicker from 'material-ui/DatePicker';
import DropDownMenu from 'material-ui/DropDownMenu';
import MenuItem from 'material-ui/MenuItem';
import RaisedButton from 'material-ui/RaisedButton';
import Paper from 'material-ui/Paper';
import Fetch from 'react-fetch';
import EditorAce from './EditorAce.jsx';
import Dialog from 'material-ui/Dialog';

class ExercisesTab extends React.Component {

    constructor() {
        super();
        this.state = {
            open: true,
            opendialog: false,
            selectedProgrammingLanguage: 1,
            selectedCase1Type: 1,
            selectedCase2Type: 1,
            value: 1,
            dialog: '',
            case1: '',
            case2: '',
            case1Type: 1,
            case2Type: 1,
            language: 'javascript',
            template: '',
            solutionpattern: ''
        };
        this.handleChangeProgrammingLanguage = this.handleChangeProgrammingLanguage.bind(this);
        this.handleOpenDialog = this.handleOpenDialog.bind(this);
        this.handleCloseDialog = this.handleCloseDialog.bind(this);
        this.handleCreateExercise = this.handleCreateExercise.bind(this);
        this.handleChangeCase1Type = this.handleChangeCase1Type.bind(this);
        this.handleChangeCase2Type = this.handleChangeCase2Type.bind(this);
        this.handleChangePatternSolution = this.handleChangePatternSolution.bind(this);
        this.handleChangeTemplate = this.handleChangeTemplate.bind(this);
    }

    handleChangeProgrammingLanguage(event, index, value) {
        this.setState({ selectedProgrammingLanguage: value });
        if (value === 1) {
            this.setState({ language: 'javascript' });
        }
        else if (value === 2) {
            this.setState({ language: 'java' });
        }
    }

    handleChangeCase1Type(event, index, value) {
        this.setState({ selectedCase1Type: value });
        if (value === 1) {
            this.setState({ case1Type: 'String' });
        }
        else if (value === 2) {
            this.setState({ case1Type: 'int' });
        }
        else if (value === 3) {
            this.setState({ case1Type: 'boolean' });
        }
        else if (value === 4) {
            this.setState({ case1Type: 'long' });
        }
    }

    handleChangeCase2Type(event, index, value) {
        this.setState({ selectedCase2Type: value });
        if (value === 1) {
            this.setState({ case2Type: 'String' });
        }
        else if (value === 2) {
            this.setState({ case2Type: 'int' });
        }
        else if (value === 3) {
            this.setState({ case2Type: 'boolean' });
        }
        else if (value === 4) {
            this.setState({ case2Type: 'long' });
        }
    }

    handleOpenDialog(event, index, value) {
        this.setState({ opendialog: true });
    }
    handleCloseDialog(event, index, value) {
        this.setState({ opendialog: false });
    }

    handleCreateExercise(e) {
        var cx = /[\u0000\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
            escapable = /[\\\"\x00-\x1f\x7f-\x9f\u00ad\u0600-\u0604\u070f\u17b4\u17b5\u200c-\u200f\u2028-\u202f\u2060-\u206f\ufeff\ufff0-\uffff]/g,
            meta = { // table of character substitutions
                '\b': '\\b',
                '\t': '\\t',
                '\n': '\\n',
                '\f': '\\f',
                '\r': '\\r',
                '"': '\\"',
                '\\': '\\\\'
            };

        function json_quote(string) {
            escapable.lastIndex = 0;
            return escapable.test(string) ? '' + string.replace(escapable, function (a) {
                var c = meta[a];
                return typeof c === 'string' ? c :
                    '\\u' + ('0000' + a.charCodeAt(0).toString(16)).slice(-4);
            }) + '' : '' + string + '';
        }


        var title = $("#title")[0].value.toString().trim();
        var description = $("#description")[0].value.toString().trim();
        var language = $("#language").text().trim();
        var youtube = $("#youtube")[0].value.toString().trim();
        var patternSolution = json_quote(this.state.solutionPattern);
        var template = json_quote(this.state.template);
        var inputArray = [];
        inputArray.push($("#inputArray")[0].value.toString());
        var entryMethod = $("#entryMethod")[0].value.toString().trim();

        if ((title || description) == "") {
            this.setState({
                opendialog: true,
                dialog: "Please fill in a title and a description"
            });
        }
        else if ((inputArray || entryMethod) == "") {
            this.setState({
                opendialog: true,
                dialog: "Please fill testcases and entryMethod"
            });
        }
        else if (patternSolution == "") {
            this.setState({
                opendialog: true,
                dialog: "Please enter a solution"
            });
        }
        else {
            $.ajax({
                url: "http://localhost:8181/docent/exercise",
                dataType: 'json',
                method: 'POST',
                xhrFields: {
                    withCredentials: true
                },
                crossDomain: true,
                data: JSON.stringify({
                    "title": title,
                    "description": description,
                    "language": language,
                    "patternSolution": patternSolution,
                    "template": template,
                    "videoLink": youtube,
                    "input": inputArray,
                    "entryMethod": entryMethod
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
                        dialog: "An error has occurred. Please make sure that you are logged in and have the permission to create new exercises."
                    });
                }.bind(this)
            });
        }
    }

    handleChangePatternSolution(event) {
        this.setState({ solutionPattern: event });
    }

    handleChangeTemplate(event) {
        this.setState({ template: event });
    }

    render() {
        return (
            <div>
                <Card className="card">
                    <Divider />
                    <CardText className="loginbody">
                        <h4>Main Information</h4>
                        <Paper zDepth={2} style={{ background: "#d1d1d1", padding: "2%" }}>
                            <table style={{ width: "100%", verticalAlign: "top" }}>
                                <tbody>
                                    <tr>
                                        <td style={{ width: "50%", padding: "6px", paddingTop: "0" }}>
                                            <TextField
                                                id="title"
                                                floatingLabelText="Title"
                                                fullWidth={false}
                                                underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                                                floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                                            />
                                        </td>
                                        <td style={{ width: "50%", padding: "6px", verticalAlign: "top" }}>
                                            <DropDownMenu className="language" id="language" value={this.state.selectedProgrammingLanguage} onChange={this.handleChangeProgrammingLanguage}>
                                                <MenuItem value={1} primaryText="JavaScript" />
                                                <MenuItem value={2} primaryText="Java" />
                                            </DropDownMenu>
                                        </td>
                                    </tr>
                                    <tr>
                                        <td style={{ width: "50%", padding: "6px" }}>
                                            <TextField
                                                id="description"
                                                floatingLabelText="Description"
                                                multiLine={true}
                                                rows={3}
                                                fullWidth={true}
                                                underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                                                floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                                            />
                                        </td>
                                        <td style={{ width: "50%", padding: "6px", verticalAlign: "bottom" }}>
                                            <TextField
                                                id="youtube"
                                                floatingLabelText="Youtube-Link"
                                                underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                                                floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                                                fullWidth={true}
                                            />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </Paper>
                        <br />
                        <br />
                        <table style={{ width: "100%" }}>
                            <tbody>
                                <tr>
                                    <td style={{ width: "50%", padding: "6px", paddingLeft: "0px" }}>
                                        <h4>Pattern Solution</h4>
                                        <Paper zDepth={4} value='test'>
                                            <EditorAce mode={this.state.language} value={this.state.solutionPattern} handleChange={this.handleChangePatternSolution} id="patternSolution" />
                                        </Paper>
                                    </td>
                                    <td style={{ width: "50%", padding: "6px", paddingRight: "0px" }}>
                                        <h4>Template</h4>
                                        <Paper zDepth={4}>
                                            <EditorAce mode={this.state.language} value={this.state.template} handleChange={this.handleChangeTemplate} id="template" />
                                        </Paper>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <h4>Testcases</h4>
                        <Paper zDepth={2} className="paper" width="100%">
                            <table className="paper" style={{ width: "100%", verticalAlign: "top" }}>
                                <tbody>
                                    <tr>
                                        <td style={{ width: "45%" }}>
                                            <TextField
                                                id="entryMethod"
                                                floatingLabelText="Name of the entry-method by Java"
                                                fullWidth={false}
                                                underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                                                floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                                            />
                                        </td>

                                        <td style={{ width: "48%" }}>
                                            <TextField
                                                id="inputArray"
                                                floatingLabelText="Test-Inputdata"
                                                fullWidth={true}
                                                underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                                                floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                                                hintText='"input1", "input2", "input3"'
                                            />
                                        </td>
                                    </tr>
                                </tbody>
                            </table>
                        </Paper>
                    </CardText>
                    <CardActions className="footer">
                        <RaisedButton
                            label="Create"
                            onClick={this.handleCreateExercise}
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
        );
    }
}

export default ExercisesTab;