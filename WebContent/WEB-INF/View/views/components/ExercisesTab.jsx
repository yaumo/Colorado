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
            value: 1,
            language: 'javascript'
        };
        this.handleChangeProgrammingLanguage = this.handleChangeProgrammingLanguage.bind(this);
        this.handleOpenDialog = this.handleOpenDialog.bind(this);
        this.handleCloseDialog = this.handleCloseDialog.bind(this);
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
    handleOpenDialog(event, index, value) {
        this.setState({ opendialog: true });
    }
    handleCloseDialog(event, index, value) {
        this.setState({ opendialog: false });
    }

    handleCreateExercise() {
        var title = $("#title")[0].value.toString();
        var description = $("#description")[0].value.toString();
        var language = $("#language").text();
        var youtube = $("#youtube")[0].value.toString();
        //var patternSolution = $("#patternSolution")[0].value.toString();
        //var template = $("#template")[0].value.toString();


        $.ajax({
            url: "http://localhost:8080/exercise",
            dataType: 'json',
            method: 'POST',
            data: JSON.stringify({
                "title": title,
                "description": description,
                "language" : language,
                //"patternSolution": patternSolution
                //"template": template,
                "youtube": youtube
            }),
            success: function (response) {

            }.bind(this)
        });
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
                                                id = "title"
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
                                             id = "description"
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
                                             id = "youtube"
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
                                        <Paper zDepth={4}>
                                            <EditorAce mode={this.state.language}  id = "patternSolution" />
                                        </Paper>
                                    </td>
                                    <td style={{ width: "50%", padding: "6px", paddingRight: "0px" }}>
                                        <h4>Template</h4>
                                        <Paper zDepth={4}>
                                            <EditorAce mode={this.state.language}   id = "template"/>
                                        </Paper>
                                    </td>
                                </tr>
                            </tbody>
                        </table>
                        <h4>Testcases</h4>
                        <Paper zDepth={2} className="paper" width="100%">
                            <table className="paper" width="100%">
                                <tbody>
                                    <tr>
                                        <td style={{ width: "33%", padding: "6px" }}>
                                            <TextField
                                             id = "case1"
                                                floatingLabelText="Case 1: Input"
                                                fullWidth={false}
                                                width="12%"
                                                underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                                                floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                                            />
                                        </td>
                                        <td style={{ width: "33%", padding: "6px" }}>
                                            <TextField
                                             id = "case2"
                                                floatingLabelText="Case 2: Input"
                                                fullWidth={false}
                                                width="12%"
                                                underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                                                floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                                            />
                                        </td>

                                        <td style={{ width: "33%", padding: "6px" }}>
                                            <TextField
                                             id = "case3"
                                                floatingLabelText="Case 3: Input"
                                                fullWidth={false}
                                                width="12%"
                                                underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                                                floatingLabelFocusStyle={{ 'color': '#bd051f' }}
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
        );
    }
}

export default ExercisesTab;