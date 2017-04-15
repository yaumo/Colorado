import React from 'react';
import ReactDom from 'react-dom';
import { Card, CardActions, CardHeader, CardMedia, CardTitle, CardText } from 'material-ui/Card';
import Divider from 'material-ui/Divider';
import { Table, TableBody, TableFooter, TableHeader, TableHeaderColumn, TableRow, TableRowColumn }
    from 'material-ui/Table';
import Paper from 'material-ui/Paper';
import DropDownMenu from 'material-ui/DropDownMenu';
import MenuItem from 'material-ui/MenuItem';
import RaisedButton from 'material-ui/RaisedButton';
import IconButton from 'material-ui/IconButton';
import ActionCode from 'material-ui/svg-icons/action/code';
import ActionSearch from 'material-ui/svg-icons/action/search';
import Popover from 'material-ui/Popover';
import EditorAce from './EditorAce.jsx';
import Menu from 'material-ui/Menu';
import Avatar from 'material-ui/Avatar';
import TextField from 'material-ui/TextField';

var lecture='';
var exercise='';
var name='';
var lecturesjson;
const courselist=[];
const lecturelist=[];
const exerciselist=[];

const tableData = [
    {
        name: 'John Smith',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'Done',
    },
    {
        name: 'Randal White',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'In Progress',
    },
    {
        name: 'Stephanie Sanders',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'In Progress',
    },
    {
        name: 'Steve Brown',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'In Progress',
    },
    {
        name: 'Joyce Whitten',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'In Progress',
    },
    {
        name: 'Samuel Roberts',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'Done',
    },
    {
        name: 'Adam Moore',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'Done',
    },
];


function handleClick(e) {
    fetch('http://localhost:8080/user', {
        method: 'POST',
        mode: 'no-cors',
        credentials: 'same-origin',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            objectClass: 'user',
            crud: '2',
            id: ""
        })
    }).then(function (response) {

    }).catch(function (err) {
        console.log(err)
    });
};



class OverviewTab extends React.Component {
    constructor() {
        super();
        this.state = {
            open: false,
            value: 1,
			selectedLecture: 0,
			selectedCourse:0,
			selectedExercise:0,
			disabledDropDownExercise: true,
			row: 0
        };
		this.handleChangeLecture = this.handleChangeLecture.bind(this);
		this.handleChangeCourse = this.handleChangeCourse.bind(this);
		this.handleChangeExercise = this.handleChangeExercise.bind(this);
		this.handleClickViewCode = this.handleClickViewCode.bind(this);
		this.handleRequestClose = this.handleRequestClose.bind(this);
    }

	componentWillMount() {
		var lectures='{"course": [	{"course_title":"WWI14SEA",	 "course_id":"1",	 "lectures":[		{"lecture_title":"Datenbanken",		"lecture_id": "0001",		"exercises": [			{ "exercise_title":"Fibonacci", "exercise_id":"0123123"},			{ "exercise_title":"Test2", "exercise_id":"0123124"},			{ "exercise_title":"Test3", "exercise_id":"0123125"}		]},		{"lecture_title":"Webprogrammierung",		"lecture_id": "0002",		"exercises": [			{ "exercise_title":"WEB1", "exercise_id":"0123123"},			{ "exercise_title":"WEB2", "exercise_id":"1123124"},			{ "exercise_title":"WEB3", "exercise_id":"1123125"}		]},		{"lecture_title":"Test2",		"lecture_id": "0003",		"exercises": [			{ "exercise_title":"Test1", "exercise_id":"2123123"},			{ "exercise_title":"Test2", "exercise_id":"2123124"},			{ "exercise_title":"Test3", "exercise_id":"2123125"}	]}	 ]},	{"course_title":"WWI14AMA",	 "course_id":"1",	 "lectures":[		{"lecture_title":"Datenbanken",		"lecture_id": "0001",		"exercises": [			{ "exercise_title":"Fibonacci", "exercise_id":"0123123"},			{ "exercise_title":"Test2", "exercise_id":"0123124"},			{ "exercise_title":"Test3", "exercise_id":"0123125"}		]},		{"lecture_title":"Webprogrammierung",		"lecture_id": "0002",		"exercises": [			{ "exercise_title":"WEB1", "exercise_id":"0123123"},			{ "exercise_title":"WEB2", "exercise_id":"1123124"},			{ "exercise_title":"WEB3", "exercise_id":"1123125"}		]},		{"lecture_title":"Test",		"lecture_id": "0003",		"exercises": [			{ "exercise_title":"Test1", "exercise_id":"2123123"},			{ "exercise_title":"Test2", "exercise_id":"2123124"},			{ "exercise_title":"Test3", "exercise_id":"2123125"}		]}	 ]}   ]}';
		lecturesjson= JSON.parse(lectures);
		
		if(courselist.length===0){
			for(var i=0;i<lecturesjson.course.length;i++){
				courselist.push(<MenuItem value={i} key={i} primaryText={lecturesjson.course[i].course_title} />);
			}
		}
		if(lecturelist.length===0){
			lecturelist.push(<MenuItem value={0} key={0} primaryText={'All Lectures'} />);
			for(var j=1;j<=lecturesjson.course[0].lectures.length;j++){
				lecturelist.push(<MenuItem value={j} key={j} primaryText={lecturesjson.course[0].lectures[j-1].lecture_title} />);
			}
		}
		
		exerciselist.push(<MenuItem value={0} key={0} primaryText={'Select a Lecture'} />);
		/*if(exerciselist.length===0){
			exerciselist.push(<MenuItem value={0} key={0} primaryText={'All Exercises'} />);
			for(var k=1;j<lecturesjson.course[0].lectures[0].exercises.length;k++){
				exerciselist.push(<MenuItem value={k} key={k} primaryText={lecturesjson.course[0].lectures[0].exercises[k].exercise_title} />);
			}
		}*/
	}
	
	handleChangeCourse(event, index, value){
		var countLectures = lecturelist.length;
		var countExercises = exerciselist.length;
		
		for(var i =0;i<countLectures;i++){
			lecturelist.pop();
		}
		for(var i =0;i<countExercises;i++){
			exerciselist.pop();
		}
		lecturelist.push(<MenuItem value={0} key={0} primaryText={'All Lectures'} />);
		for(var j=1;j<=lecturesjson.course[value].lectures.length;j++){
			lecturelist.push(<MenuItem value={j} key={j} primaryText={lecturesjson.course[value].lectures[j-1].lecture_title} />);
		}
		
		exerciselist.push(<MenuItem value={0} key={0} primaryText={'Select a Lecture'} />);
		/*
		for(var k=0;j<lecturesjson.course[value].lectures[0].exercises.length;k++){
			lecturelist.push(<MenuItem value={j} key={j} primaryText={lecturesjson.course[value].lectures[0].exercises[k].exercise_title} />);
		}
		*/
		
		this.setState({selectedCourse: value});
		this.setState({selectedLecture: 0});
		this.setState({selectedExercise: 0});
	}
	
	handleChangeLecture(event, index, value){
		var count = exerciselist.length;
		for(var i =0;i<count;i++){
			exerciselist.pop();
		}
		if(value!=0)
		{
			exerciselist.push(<MenuItem value={0} key={0} primaryText={'All Exercises'} />);
			this.setState({disabledDropDownExercise: false});
			for(var k=1;k<=lecturesjson.course[this.state.selectedCourse].lectures[value-1].exercises.length;k++){
				exerciselist.push(<MenuItem value={k} key={k} primaryText={lecturesjson.course[this.state.selectedCourse].lectures[value-1].exercises[k-1].exercise_title} />);
			}
		}
		else{
			exerciselist.push(<MenuItem value={0} key={0} primaryText={'Select a Lecture'} />);
			this.setState({disabledDropDownExercise: true});
		}
		this.setState({selectedLecture: value});
		this.setState({selectedExercise: 0});
	}
	
	handleChangeExercise(event, index, value){
		this.setState({selectedExercise: value});
	}
	
	handleClickViewCode(event, index, value){
		
		lecture=event.currentTarget.parentElement.parentElement.cells[1].innerText;
		exercise=event.currentTarget.parentElement.parentElement.cells[2].innerText;
		name=event.currentTarget.parentElement.parentElement.cells[3].innerText;
		this.setState({
			open: true,
		});
	}
	handleRequestClose(){
		this.setState({open: false});
	}

    render() {
        return (
            <div>
                <Card>
                    <Divider />
                    <CardText className="loginbody">
                        <Paper zDepth={4}>
                            <Table selectable={false}>
                                <TableHeader displaySelectAll={false} adjustForCheckbox={false}>
                                    <TableRow>
                                        <TableHeaderColumn colSpan="6" style={{ textAlign: 'center', background: "#d1d1d1" }}>
                                            <DropDownMenu value={this.state.selectedCourse} onChange={this.handleChangeCourse}>
												{courselist}
                                            </DropDownMenu>
                                            <DropDownMenu value={this.state.selectedLecture} onChange={this.handleChangeLecture}>
                                                {lecturelist}
                                            </DropDownMenu>
											
                                            <DropDownMenu disabled={this.state.disabledDropDownExercise} value={this.state.selectedExercise} onChange={this.handleChangeExercise} ref={'dropdownexercise'}>
                                                {exerciselist}
                                            </DropDownMenu>
											
                                            <IconButton onClick={handleClick}>
                                                    <ActionSearch/>
                                                </IconButton>
                                        </TableHeaderColumn>
                                    </TableRow>
                                    <TableRow style={{ background: "#d1d1d1" }}>
                                        <TableHeaderColumn>Course</TableHeaderColumn>
                                        <TableHeaderColumn>Lecture</TableHeaderColumn>
                                        <TableHeaderColumn>Exercise</TableHeaderColumn>
                                        <TableHeaderColumn>Name</TableHeaderColumn>
                                        <TableHeaderColumn>Status</TableHeaderColumn>
                                        <TableHeaderColumn>View Code</TableHeaderColumn>
                                    </TableRow>
                                </TableHeader>
                                <TableBody displayRowCheckbox={false} style={{ background: "#d1d1d1" }}>
                                    {tableData.map((row, index) => (
                                        <TableRow key={index} selected={row.selected}>
                                            <TableRowColumn>{"WWI14SEA"}</TableRowColumn>
                                            <TableRowColumn>{row.lecture}</TableRowColumn>
                                            <TableRowColumn>{row.exercise}</TableRowColumn>
                                            <TableRowColumn>{row.name}</TableRowColumn>
                                            <TableRowColumn>{row.status}</TableRowColumn>
                                            <TableRowColumn>
                                                <IconButton onClick={this.handleClickViewCode}>
                                                    <ActionCode/>
                                                </IconButton>
                                            </TableRowColumn>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
							<Popover
							open={this.state.open}
							style={{ marginTop: '20%', marginLeft: '35%', width: 'auto', height:'auto'}}
							onRequestClose={this.handleRequestClose}
							>
							 
							<Card>
								<CardHeader className="loginheader">
								  <Avatar
									src="images/colorado.jpg"
									size={60}
									className="coloradologo"
									/>
								</CardHeader>
								<CardText className="loginbody">
									<table className="paper" width="100%">
										<tbody>
											<tr>
												<td>
													<TextField
														floatingLabelText={lecture}
														fullWidth={true}
														width="3%"
														disabled={true}
														underlineShow={false}
														
													/>
												</td>
												<td>
													<TextField
														floatingLabelText={exercise}
														fullWidth={true}
														width="3%"
														disabled={true}
														underlineShow={false}
													/>
												</td>
												<td>
													<TextField
														floatingLabelText={name}
														fullWidth={true}
														width="3%"
														disabled={true}
														underlineShow={false}
													/>
												</td>
											</tr>
										</tbody>
									</table>
									<Paper zDepth={4}>
                                            <EditorAce mode='javascript'/>
									</Paper>
								</CardText>
								<CardActions className="footer">
									<RaisedButton 
									label="Close" 
									onClick={this.handleRequestClose}
									backgroundColor="#bd051f"
									labelColor="#FFFFFF"/>
								</CardActions>
							</Card>
							</Popover>
                        </Paper>
                    </CardText>
                    <CardActions className="footer">
                    </CardActions>
                </Card>
            </div>
        );
    }
}

export default OverviewTab;