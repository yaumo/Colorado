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


function getAllCourses() {
    fetch('http://localhost:8080/course', {
        method: 'POST',
        credentials: 'same-origin',
        headers: {
            'Accept': 'application/json'
        },
        body: JSON.stringify({
            objectClass: 'course',
            crud: '2'
        })
    }).then(function (response) {
        return response.json();
    }).then(function (courses) {
        console.log(courses);
        console.log(courses[0]["0x1"].title);

    }).catch(function (err) {
        console.log(err);
    });
}


function getAllLectures() {
    fetch('http://localhost:8080/lecture', {
        method: 'POST',
        mode: 'no-cors',
        credentials: 'same-origin',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            objectClass: 'lecture',
            crud: '2'
        })
    }).then(function (response) {
        return response.json();

    }).catch(function (err) {
        console.log(err)
    });
}

function createNewLecture() {
    fetch('http://localhost:8080/lecture', {
        method: 'POST',
        mode: 'no-cors',
        credentials: 'same-origin',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            objectClass: 'lecture',
            crud: '1',
            title: 'LectureTest2'
        })
    }).then(function (response) {

    }).catch(function (err) {
        console.log(err)
    });
}

function handleClick(e) {
    getAllCourses();
    //createNewLecture();
    //getAllLectures();
};

const tableData = [
    {
        name: 'John Smith',
        mail: 'd12345@docent.dhbw-mannheim.de',
    },
    {
        name: 'Randal White',
        mail: 'd12345@docent.dhbw-mannheim.de',
    },
    {
        name: 'Stephanie Sanders',
        mail: 'd12345@docent.dhbw-mannheim.de',
    },
    {
        name: 'Steve Brown',
        mail: 'd12345@docent.dhbw-mannheim.de',
    },
    {
        name: 'Joyce Whitten',
        mail: 'd12345@docent.dhbw-mannheim.de',
    },
    {
        name: 'Samuel Roberts',
        mail: 'd12345@docent.dhbw-mannheim.de',
    },
    {
        name: 'Adam Moore',
        mail: 'd12345@docent.dhbw-mannheim.de',
    },
];


function handleChange(event, index, value) {
    this.setState({ value });
};

class LecturesTab extends React.Component {

    constructor() {
        super();
        this.state = {
            open: true,
            value: 1,
			selectedCourse:1
        };
		this.handleChangeCourse = this.handleChangeCourse.bind(this);
    }
	handleChangeCourse(event, index, value){
		this.setState({selectedCourse: value});
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
                                    <MenuItem value={1} primaryText="WWI14SEA" />
                                    <MenuItem value={2} primaryText="WWI14AMA" />
                                    <MenuItem value={3} primaryText="WWI16SEB" />
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
                                    </TableRow>
                                </TableHeader>
                                <TableBody deselectOnClickaway={false}>
                                    {tableData.map((row, index) => (
                                        <TableRow key={index} selected={row.selected}>
                                            <TableRowColumn>{row.name}</TableRowColumn>
                                            <TableRowColumn>{row.mail}</TableRowColumn>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>

                        </Paper>
                    </CardText>
                    <CardActions className="footer">
                        <RaisedButton
                            label="Create"
                            onClick={handleClick}
                            backgroundColor="#bd051f"
                            labelColor="#FFFFFF" />
                    </CardActions>
                </Card>
            </div>
        );
    }
}

export default LecturesTab;