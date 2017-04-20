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
            courselist: [],
            tableData: []
        };
        this.handleChangeCourse = this.handleChangeCourse.bind(this);
        this.handleOpenDialog = this.handleOpenDialog.bind(this);
        this.handleCloseDialog = this.handleCloseDialog.bind(this);
    }
    handleChangeCourse(event, index, value) {
        this.setState({ selectedCourse: value });
    }

    componentDidMount() {
        $.ajax({
            url: "http://localhost:8080/courses",
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
						//courseids.push(coursesJSON[i].id);
                    }
                }
                this.setState({ courselist: courselist });
            }.bind(this)
        });


        $.ajax({
            url: "http://localhost:8080/users",
            dataType: 'json',
            method: 'GET',
            xhrFields: {
                withCredentials: true
            },
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

    }
	
	handleChangeCourse(event, index, value){
		this.setState({selectedCourse: value});
		//this.setState({selectedcourseid: courseids[value]});
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
                            >
                                <TableHeader displaySelectAll={false} >
                                    <TableRow>
                                        <TableHeaderColumn>Name</TableHeaderColumn>
                                        <TableHeaderColumn>E-Mail</TableHeaderColumn>
                                        <TableHeaderColumn className="hidden">userID</TableHeaderColumn>
                                    </TableRow>
                                </TableHeader>
                                <TableBody deselectOnClickaway={false}>
                                    {this.state.tableData.map((row, index) => (
                                        <TableRow key={index} selected={row.selected}>
                                            <TableRowColumn>{row.username}</TableRowColumn>
                                            <TableRowColumn>{row.mail}</TableRowColumn>
                                            <TableRowColumn className="hidden">{row.hibId}</TableRowColumn>
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

export default LecturesTab;