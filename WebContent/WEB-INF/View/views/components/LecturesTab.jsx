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


const courselist=[];



function getAllCourses() {
    fetch('http://localhost:8080/courses', {
        method: 'GET',
        credentials: 'same-origin',
        mode: 'no-cors',
        headers: {
            'Accept': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (courses) {
        console.log(courses.length)
    }).catch(function (err) {
        console.log(err);
    });
}

function getCurrentCourse() {
    fetch('http://localhost:8080/course', {
        method: 'GET',
        credentials: 'same-origin',
        mode: 'no-cors',
        headers: {
            'Accept': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (courses) {
        console.log(courses.length)
    }).catch(function (err) {
        console.log(err);
    });
}

function getAllUsers() {
    fetch('http://localhost:8080/users', {
        method: 'GET',
        credentials: 'same-origin',
        mode: 'no-cors',
        headers: {
            'Accept': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).then(function (courses) {
        console.log(courses.length)
    }).catch(function (err) {
        console.log(err);
    });
}


function getAllLectures() {
    fetch('http://localhost:8080/lecture', {
        method: 'GET',
        credentials: 'same-origin',
        headers: {
            'Accept': 'application/json'
        }
    }).then(function (response) {
        return response.json();

    }).catch(function (err) {
        console.log(err)
    });
}

function getCurrentUser() {
    fetch('http://localhost:8080/user', {
        method: 'GET',
        credentials: 'same-origin',
        mode: 'no-cors',
        headers: {
            'Accept': 'application/json'
        }
    }).then(function (response) {
        return response.json();
    }).catch(function (err) {
        console.log(err)
    });
}

function handleClick(e) {
    getAllUsers();
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
			selectedCourse:0
        };
		this.handleChangeCourse = this.handleChangeCourse.bind(this);
    }
	handleChangeCourse(event, index, value){
		this.setState({selectedCourse: value});
	}
    componentWillMount(){
        getAllCourses();
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
                                    {courselist}
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