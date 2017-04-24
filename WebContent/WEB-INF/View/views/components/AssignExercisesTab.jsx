import React from 'react';
import ReactDom from 'react-dom';
import { Card, CardActions, CardHeader, CardMedia, CardTitle, CardText } from 'material-ui/Card';
import Divider from 'material-ui/Divider';
import TextField from 'material-ui/TextField';
import DatePicker from 'material-ui/DatePicker';
import DropDownMenu from 'material-ui/DropDownMenu';
import MenuItem from 'material-ui/MenuItem';
import { Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn } from 'material-ui/Table';
import Paper from 'material-ui/Paper';
import RaisedButton from 'material-ui/RaisedButton';
import Dialog from 'material-ui/Dialog';


const courselist = [];
const courseids = [];
const lecturelist = [];
const lectureids = [];
var exercisesTableData = [];
var exercisesJSON;
var coursesJSON;


class AssignExercisesTab extends React.Component {

    constructor() {
        super();
        this.state = {
            open: true,
            opendialog: false,
            value: 1,
            selectedLecture: 0,
            selectedCourse: 0,
            selectedcourseid: '',
            exerciselist: [],
            lecturelist: [],
            selectedlectureid: '',
            exercisesTableData: [],
            selction: []
        };

        this.handleChangeLecture = this.handleChangeLecture.bind(this);
        this.handleRowSelection = this.handleRowSelection.bind(this);
        this.handleChangeCourse = this.handleChangeCourse.bind(this);
        this.handleAssignClick = this.handleAssignClick.bind(this);
        this.handleOpenDialog = this.handleOpenDialog.bind(this);
        this.handleCloseDialog = this.handleCloseDialog.bind(this);
    }

    componentDidMount() {
        $.ajax({
            url: "http://localhost:8181/exercises",
            dataType: 'json',
            method: 'GET',
            xhrFields: {
                withCredentials: true
            },
            success: function (exercises) {
                for (var i = 0; i < exercises.length; i++) {
                    var d = new Date(exercises[i].creationDate);
                    exercises[i].creationDate = d.toDateString();
                }
                this.setState({ exercisesTableData: exercises });
            }.bind(this)
        });

        $.ajax({
            url: "http://localhost:8181/courses",
            dataType: 'json',
            method: 'GET',
            xhrFields: {
                withCredentials: true
            },
            success: function (courses) {
                coursesJSON = courses;
                if (courselist.length === 0) {
                    for (var i = 0; i < coursesJSON.length; i++) {
                        courselist.push(<MenuItem value={i} key={i} primaryText={coursesJSON[i].title} />);
                        courseids.push(coursesJSON[i].id);
                    }
                }
                if (lecturelist.length === 0) {
                    lecturelist.push(<MenuItem value={0} key={0} primaryText={'Select a Lecture'} />);
                    lectureids.push('');
                    for (var j = 1; j <= coursesJSON[0].lectures.length; j++) {
                        lecturelist.push(<MenuItem value={j} key={j} id={coursesJSON[0].lectures.id} primaryText={coursesJSON[0].lectures[j - 1].title} />);
                        lectureids.push(coursesJSON[0].lectures[j - 1].id);
                    }
                }
                this.setState({ selectedcourseid: courseids[0] });
                this.setState({ selectedlectureid: lectureids[0] });
                this.setState({ courselist: courselist });
                this.setState({ lecturelist: lecturelist });
            }.bind(this)
        });
    }

    handleChangeCourse(event, index, value) {
        var count = lecturelist.length;
        for (var i = 0; i < count; i++) {
            lecturelist.pop();
            lectureids.pop();
        }
        lecturelist.push(<MenuItem value={0} key={0} primaryText={'Select a Lecture'} />);
        lectureids.push('');
        for (var j = 0; j < coursesJSON[value].lectures.length; j++) {
            lecturelist.push(<MenuItem value={j} key={j} primaryText={coursesJSON[value].lectures[j - 1].title} />);
            lectureids.push(coursesJSON[value].lectures[j - 1].id);
        }

        this.setState({ selectedlectureid: lectureids[0] });
        this.setState({ selectedLecture: 0 });
        this.setState({ selectedcourseid: courseids[value] });
        this.setState({ selectedCourse: value });
    }

    handleChangeLecture(event, index, value) {
        this.setState({ selectedLecture: value });
        this.setState({ selectedlectureid: lectureids[value] });
    }
    handleOpenDialog(event, index, value) {
        this.setState({ opendialog: true });
    }
    handleCloseDialog(event, index, value) {
        this.setState({ opendialog: false });
    }

    handleAssignClick(e) {
        var exercises = this.state.selection;
        var exercisesJSON = [];

        for (var i = 0; i < exercises.length; i++) {
            var exerciseId = this.state.exercisesTableData[exercises[i]].id;
            exercisesJSON[i] = { "id": exerciseId };
        }

        var courseId = this.state.selectedcourseid;
        var lectureId = this.state.selectedlectureid;
        var deadline = $("#deadline").val();
        var data = JSON.stringify({
            "exercises": exercisesJSON,
            "courseId": courseId,
            "lectureId": lectureId,
            "deadline": deadline
        });

        $.ajax({
            url: "http://localhost:8181/lecture",
            dataType: 'json',
            method: 'PATCH',
            xhrFields: {
                withCredentials: true
            },
            data: data,
            success: function (response) {
                //handle response
            }.bind(this)
        });
    }

    handleRowSelection(key) {
        this.setState({ selection: key });
    }


    handleChangeCourse(event, index, value) {
        var countLectures = lecturelist.length;

        for (var i = 0; i < countLectures; i++) {
            lecturelist.pop();
        }
        lecturelist.push(<MenuItem value={0} key={0} primaryText={'Select a Lecture'} />);
        for (var j = 1; j <= coursesJSON[value].lectures.length; j++) {
            lecturelist.push(<MenuItem value={j} key={j} primaryText={coursesJSON[value].lectures[j - 1].title} />);
        }

        this.setState({ selectedCourse: value });
        this.setState({ selectedLecture: 0 });
    }
    render() {
        return (
            <div>
                <Card>
                    <Divider />
                    <CardText className="loginbody">
                        <h4>Step 1: Select Exercise(s)</h4>
                        <Paper zDepth={2} className="paper">
                            <div>
                                <Table multiSelectable={true} className="paper" onRowSelection={this.handleRowSelection}>
                                    <TableHeader>
                                        <TableRow className="paper">
                                            <TableHeaderColumn>Title</TableHeaderColumn>
                                            <TableHeaderColumn>Language</TableHeaderColumn>
                                            <TableHeaderColumn>Creation Date</TableHeaderColumn>
                                            <TableHeaderColumn className="hidden">exerciseID</TableHeaderColumn>
                                        </TableRow>
                                    </TableHeader>
                                    <TableBody deselectOnClickaway={false} className="paper">
                                        {this.state.exercisesTableData.map((row, index) => (
                                            <TableRow key={index} selected={row.selected}>
                                                <TableRowColumn>{row.title}</TableRowColumn>
                                                <TableRowColumn>{row.language}</TableRowColumn>
                                                <TableRowColumn>{row.creationDate}</TableRowColumn>
                                                <TableRowColumn className="hidden">{row.id}</TableRowColumn>
                                            </TableRow>
                                        ))}
                                    </TableBody>
                                </Table>
                            </div>
                        </Paper>

                        <br />
                        <h4>Step 2: Select Lecture</h4>
                        <Paper zDepth={2} style={{ textAlign: "center", background: "#d1d1d1" }}>
                            <DropDownMenu id="appendCourse" value={this.state.selectedCourse} onChange={this.handleChangeCourse}>
                                {this.state.courselist}
                            </DropDownMenu>

                            <DropDownMenu id="appendLecture" value={this.state.selectedLecture} onChange={this.handleChangeLecture}>
                                {this.state.lecturelist}
                            </DropDownMenu>
                        </Paper>

                        <br />
                        <h4>Step 3: Select Deadline</h4>
                        <Paper zDepth={2} style={{ textAlign: "center", background: "#d1d1d1" }}>
                            <DatePicker id="deadline" floatingLabelText="Deadline" mode="landscape" />
                        </Paper>
                    </CardText>
                    <CardActions className="footer">
                        <RaisedButton
                            label="Assign"
                            onClick={this.handleAssignClick}
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

export default AssignExercisesTab;