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
const lecturelist = [];
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
            exerciselist: [],
            lecturelist: [],
            exercisesTableData: []
        };

        this.handleChangeLecture = this.handleChangeLecture.bind(this);
        this.handleChangeCourse = this.handleChangeCourse.bind(this);
        this.handleAssignClick = this.handleAssignClick.bind(this);
        this.handleOpenDialog = this.handleOpenDialog.bind(this);
        this.handleCloseDialog = this.handleCloseDialog.bind(this);
    }

    componentDidMount() {
        $.ajax({
            url: "http://localhost:8080/exercises",
            dataType: 'json',
            method: 'GET',
            data: 'owner=2',
            success: function (exercises) {
                this.setState({ exercisesTableData: exercises });
            }.bind(this)
        });

        $.ajax({
            url: "http://localhost:8080/courses",
            dataType: 'json',
            method: 'GET',
            success: function (courses) {
                coursesJSON = courses;
                if (courselist.length === 0) {
                    for (var i = 0; i < coursesJSON.length; i++) {
                        courselist.push(<MenuItem value={i} key={coursesJSON[i].hibId} primaryText={coursesJSON[i].title} />);
                    }
                }
                if (lecturelist.length === 0) {
                    lecturelist.push(<MenuItem value={0} key={0} primaryText={'Select a Lecture'} />);
                    for (var j = 1; j <= coursesJSON[0].lectures.length; j++) {
                        lecturelist.push(<MenuItem value={j} key={j} id={coursesJSON[0].lectures.hibId} primaryText={coursesJSON[0].lectures[j - 1].title} />);
                    }
                }
                this.setState({ courselist: courselist });
                this.setState({ lecturelist: lecturelist });
            }.bind(this)
        });
    }

    handleChangeCourse(event, index, value) {
        var count = lecturelist.length;
        for (var i = 0; i < count; i++) {
            lecturelist.pop();
        }
        for (var j = 0; j < lecturesjson.course[value].lectures.length; j++) {
            lecturelist.push(<MenuItem value={j} key={j} primaryText={lecturesjson.course[value].lectures[j].lecture_title} />);
        }

        this.setState({ selectedLecture: 0 });
        this.setState({ selectedCourse: value });
    }

    handleChangeLecture(event, index, value) {
        this.setState({ selectedLecture: value });
    }
    handleOpenDialog(event, index, value) {
        this.setState({ opendialog: true });
    }
    handleCloseDialog(event, index, value) {
        this.setState({ opendialog: false });
    }

    handleAssignClick(e){
        var exercises = [];
        var courseId;
        var lectureId;
        var deadline = $("#deadline").val();

        $.ajax({
            url: "http://localhost:8080/lecture",
            dataType: 'json',
            method: 'POST',
            data: JSON.stringify({
                "exercises" : exercises,
                "courseId": courseId,
                "lectureId": lectureId,
                "deadline": deadline
            }),
            success: function (response) {
                //handle response
            }.bind(this)
        });
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

    handleChangeLecture(event, index, value) {
        this.setState({ selectedLecture: value });
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
                                <Table multiSelectable={true} className="paper" >
                                    <TableHeader>
                                        <TableRow className="paper">
                                            <TableHeaderColumn>Title</TableHeaderColumn>
                                            <TableHeaderColumn>Language</TableHeaderColumn>
                                            <TableHeaderColumn>Creation Date</TableHeaderColumn>
                                            <TableHeaderColumn className="hidden">exerciseID</TableHeaderColumn>
                                        </TableRow>
                                    </TableHeader>
                                    <TableBody deselectOnClickaway={false} className="paper">
                                            {/*this.state.exerciseTableData.map((row, index) => (
                                                <TableRow key={index} selected={row.selected}>
                                                    <TableRowColumn>{row.title}</TableRowColumn>
                                                    <TableRowColumn>{row.language}</TableRowColumn>
                                                    <TableRowColumn>{row.creationDate}</TableRowColumn>
                                                    <TableRowColumn>{row.hibId}</TableRowColumn>
                                                </TableRow>
                                            ))*/}
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
                            <DatePicker  id="deadline" floatingLabelText="Deadline" mode="landscape"  />
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