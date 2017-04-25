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


var coursesJSON;
const courselist = [];
const courseids = [];
var tableData = [];
var allDocentsJSON;



class LecturesTab extends React.Component {

    constructor() {
        super();

        this.state = {
            open: true,
            opendialog: false,
            value: 1,
            selectedCourse: 0,
            selectedcourseid: '',
            dialog: '',
            courselist: [],
            tableData: [],
            selection: []
        };
        this.handleChangeCourse = this.handleChangeCourse.bind(this);
        this.handleOpenDialog = this.handleOpenDialog.bind(this);
        this.handleCloseDialog = this.handleCloseDialog.bind(this);
        this.handleClick = this.handleClick.bind(this);
        this.handleRowSelection = this.handleRowSelection.bind(this);
    }
    handleChangeCourse(event, index, value) {
        this.setState({ selectedCourse: value });
        this.setState({ selectedcourseid: courseids[value] });
    }

    componentDidMount() {
        $.ajax({
            url: "http://localhost:8181/docent/courses",
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
                this.setState({ courselist: courselist });
                this.setState({ selectedcourseid: courseids[0] });
            }.bind(this)
        });


        $.ajax({
            url: "http://localhost:8181/docent/users",
            dataType: 'json',
            xhrFields: {
                withCredentials: true
            },
            method: 'GET',
            success: function (allDocents) {
                this.setState({ tableData: allDocents });
            }.bind(this)
        });
    }

    handleOpenDialog(event, index, value) {
        this.setState({ opendialog: true });
    }
    handleCloseDialog(event, index, value) {
        this.setState({ opendialog: false });
    }
    handleClick(e) {
        var courseID = this.state.selectedcourseid;
        var title = $("#lectureTitle")[0].value.toString();
        var tutors = this.state.selection;
        var tutorJSON = [];

        for (var i = 0; i < tutors.length; i++) {
            var userId = this.state.tableData[tutors[i]].id;
            tutorJSON[i] = { "userId": userId };
        }

        if (title === "") {
            this.setState({
                opendialog: true,
                dialog: "Please select a title"
            });
        } else {
            $.ajax({
                url: "http://localhost:8181/docent/lecture",
                dataType: 'json',
                xhrFields: {
                    withCredentials: true
                },
                method: 'POST',
                data: JSON.stringify({
                    "course": { "id": courseID },
                    "title": title,
                    "tutors": tutorJSON
                }),
                success: function (response) {
                    this.setState({
                        opendialog: true,
                        dialog: "Lecture successfully created"
                    });
                }.bind(this),
                error: function (error) {
                    console.log("läuft nicht")
                }.bind(this),
            });
        }
    }

    handleChangeCourse(event, index, value) {
        this.setState({ selectedCourse: value });
        this.setState({ selectedcourseid: courseids[value] });
    }

    handleRowSelection(key) {
        this.setState({ selection: key });
    }

    render() {
        return (
            <div>
                <Card>
                    <Divider />
                    <CardText className="loginbody">
                        <h4>Step 1: Select Course</h4>
                        <Paper zDepth={2} style={{ textAlign: "center", background: "#d1d1d1" }}>
                            <div>
                                <DropDownMenu value={this.state.selectedCourse} onChange={this.handleChangeCourse}>
                                    {this.state.courselist}
                                </DropDownMenu>
                            </div>
                        </Paper>

                        <br />
                        <h4>Step 2: Name Lecture</h4>
                        <Paper zDepth={2} style={{ textAlign: "center", background: "#d1d1d1" }}>
                            <TextField
                                id="lectureTitle"
                                floatingLabelText="Lecture Name"
                                fullWidth={false}
                                underlineFocusStyle={{ 'borderColor': '#bd051f' }}
                                floatingLabelFocusStyle={{ 'color': '#bd051f' }}
                            />
                        </Paper>

                        <br />
                        <h4>Step 3: Select Tutors</h4>
                        <Paper zDepth={2} style={{ textAlign: "center", background: "#d1d1d1" }}>
                            <Table multiSelectable={true} style={{ textAlign: "center", background: "#d1d1d1" }}
                                onRowSelection={this.handleRowSelection}
                            >
                                <TableHeader displaySelectAll={false} >
                                    <TableRow>
                                        <TableHeaderColumn>Last Name</TableHeaderColumn>
                                        <TableHeaderColumn>First Name</TableHeaderColumn>
                                        <TableHeaderColumn>Username</TableHeaderColumn>
                                        <TableHeaderColumn className="hidden">userID</TableHeaderColumn>
                                    </TableRow>
                                </TableHeader>
                                <TableBody deselectOnClickaway={false}>
                                    {this.state.tableData.map((row, index) => (
                                        <TableRow key={index} selected={row.selected}>
                                            <TableRowColumn>{row.lastName}</TableRowColumn>
                                            <TableRowColumn>{row.firstName}</TableRowColumn>
                                            <TableRowColumn>{row.username}</TableRowColumn>
                                            <TableRowColumn className="hidden">{row.id}</TableRowColumn>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>

                        </Paper>
                    </CardText>
                    <CardActions className="footer">
                        <RaisedButton
                            label="Create"
                            onClick={this.handleClick}
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

export default LecturesTab;