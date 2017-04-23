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

var lecture = '';
var exercise = '';
var name = '';
var coursesJSON;
var allUsersJSON;
var solutionsJSON;
var solutionJSON;
const courselist = [];
const lecturelist = [];
const exerciselist = [];
const userlist = [];
const overviewlist = [];
const lectureids = [];
const courseids = [];
const exerciseids = [];



class OverviewTab extends React.Component {
	constructor() {
		super();
		this.state = {
			open: false,
			value: 1,
			selectedLecture: 0,
			selectedCourse: 0,
			selectedExercise: 0,
			disabledDropDownExercise: true,
			row: 0,
			tableData: [],
			courselist: [],
			selectedcourseid: '',
			lecturelist: [],
			selectedlectureid: '',
			exerciselist: [],
			selectedexercisid: '',
			userlist: [],
			overviewlist: [{
				name: "Please select a course,",
				status: "lecture and an exercise"
			}],
			allUsers: ''
		};
		this.handleChangeLecture = this.handleChangeLecture.bind(this);
		this.handleChangeCourse = this.handleChangeCourse.bind(this);
		this.handleChangeExercise = this.handleChangeExercise.bind(this);
		this.handleClickViewCode = this.handleClickViewCode.bind(this);
		this.handleRequestClose = this.handleRequestClose.bind(this);
		this.handleClickSearch = this.handleClickSearch.bind(this);
	}

	componentDidMount() {
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
						//courseids.push(courseJSON[i].courseid);
						//this.setState({selectedcourseid: courseids[0]});
					}
				}
				if (lecturelist.length === 0) {
					lecturelist.push(<MenuItem value={0} key={0} primaryText={'All Lectures'} />);
					//lectureids.push('');
					for (var j = 1; j <= coursesJSON[0].lectures.length; j++) {
						lecturelist.push(<MenuItem value={j} key={j} primaryText={coursesJSON[0].lectures[j - 1].title} />);
						//lectureids.push(courseJSON[0].lecture[j - 1].id);
						//this.setState({selectedlectureid: lectureids[0]});
					}
				}
				if (exerciselist.length === 0) {
					exerciselist.push(<MenuItem value={0} key={0} primaryText={'Select a Lecture'} />);
					//exerciseids.push('');
					//this.setState({selectedexercisid: exerciseids[0]});
				}
				this.setState({ courselist: courselist });
				this.setState({ lecturelist: lecturelist });
				this.setState({ exerciselist: exerciselist });
			}.bind(this)
		});
	}


	handleChangeCourse(event, index, value) {
		var countLectures = lecturelist.length;
		var countExercises = exerciselist.length;

		for (var i = 0; i < countLectures; i++) {
			lecturelist.pop();
			//lectureids.pop();
		}
		for (var i = 0; i < countExercises; i++) {
			exerciselist.pop();
			//exerciseids.pop();
		}
		lecturelist.push(<MenuItem value={0} key={0} primaryText={'All Lectures'} />);
		//lectureids.push('');
		for (var j = 1; j <= coursesJSON[value].lectures.length; j++) {
			lecturelist.push(<MenuItem value={j} key={j} primaryText={coursesJSON[value].lectures[j - 1].title} />);
			//lectureids.push(courseJSON[value].lecture[j - 1].id);
			//this.setState({selectedlectureid: lectureids[0]});
		}

		exerciselist.push(<MenuItem value={0} key={0} primaryText={'Select a Lecture'} />);
		//exerciseids.push('');
		//this.setState({selectedexercisid: exerciseids[0]});

		//this.setState({selectedcourseid: courseids[value]});
		this.setState({ selectedCourse: value });
		this.setState({ selectedLecture: 0 });
		this.setState({ selectedExercise: 0 });
	}

	handleChangeLecture(event, index, value) {
		var count = exerciselist.length;
		for (var i = 0; i < count; i++) {
			exerciselist.pop();
			//exerciseids.pop();
		}
		if (value != 0) {
			exerciselist.push(<MenuItem value={0} key={0} primaryText={'All Exercises'} />);
			//exerciseids.push('');
			this.setState({ disabledDropDownExercise: false });
			for (var k = 1; k <= coursesJSON[this.state.selectedCourse].lectures[value - 1].exercises.length; k++) {
				exerciselist.push(<MenuItem value={k} key={k} primaryText={coursesJSON[this.state.selectedCourse].lectures[value - 1].exercises[k - 1].title} />);
				//exerciseids.push(this.state.selectedCourse].lectures[value - 1].exercises[k - 1].id)
			}
		}
		else {
			exerciselist.push(<MenuItem value={0} key={0} primaryText={'Select a Lecture'} />);
			this.setState({ disabledDropDownExercise: true });
			//exerciseids.push('');
		}
		this.setState({ selectedLecture: value });
		this.setState({ selectedExercise: 0 });
		//this.setState({selectedexercisid: exerciseids[0]});
	}

	handleChangeExercise(event, index, value) {
		this.setState({ selectedExercise: value });
		//this.setState({ selectedexercisid: exerciseids[value]});
	}

	handleClickViewCode(event, index, value) {
		$.ajax({
			url: "http://localhost:8181/docent/solution",
			dataType: 'json',
			method: 'GET',
			xhrFields: {
				withCredentials: true
			},
			//data: 'owner=2 exerciseID=1',
			success: function (solution) {
				solutionJSON = solution;
				lecture = event.currentTarget.parentElement.parentElement.cells[1].innerText;
				exercise = event.currentTarget.parentElement.parentElement.cells[2].innerText;
				name = event.currentTarget.parentElement.parentElement.cells[3].innerText;

				this.setState({
					open: true
					//code noch changen
				});

			}.bind(this)
		});


	}

	handleRequestClose() {
		this.setState({ open: false });
	}

	handleClickSearch(e) {
		$.ajax({
			url: "http://localhost:8181/docent/solutions",
			dataType: 'json',
			method: 'GET',
			xhrFields: {
				withCredentials: true
			},
			//data: 'lectureID=2 exerciseID=1',
			success: function (solutions) {
				solutionsJSON = solution;
				this.setState({
					overviewlist: solutionsJSON
				});
			}.bind(this),
			error: function (error) {
				//handle error
			}.bind(this)
		});
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
											<DropDownMenu value={this.state.selectedCourse} onChange={this.handleChangeCourse} ref={'DropDownCourse'}>
												{this.state.courselist}
											</DropDownMenu>
											<DropDownMenu value={this.state.selectedLecture} onChange={this.handleChangeLecture} ref={'DropDownLecture'}>
												{this.state.lecturelist}
											</DropDownMenu>

											<DropDownMenu disabled={this.state.disabledDropDownExercise} value={this.state.selectedExercise} onChange={this.handleChangeExercise} ref={'DropDownExercise'}>
												{this.state.exerciselist}
											</DropDownMenu>

											<IconButton onClick={this.handleClickSearch}>
												<ActionSearch />
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
										<TableHeaderColumn className="hidden">exerciseID</TableHeaderColumn>
										<TableHeaderColumn className="hidden">userID</TableHeaderColumn>
										<TableHeaderColumn className="hidden">lectureID</TableHeaderColumn>
									</TableRow>
								</TableHeader>
								<TableBody displayRowCheckbox={false} style={{ background: "#d1d1d1" }}>
									{this.state.overviewlist.map((row, index) => (
										<TableRow key={index} selected={row.selected}>
											<TableRowColumn>{this.state.selectedCourse}</TableRowColumn>
											<TableRowColumn>{this.state.selectedLecture}</TableRowColumn>
											<TableRowColumn>{this.state.selectedExercise}</TableRowColumn>
											<TableRowColumn>{row.name}</TableRowColumn>
											<TableRowColumn>{row.status}</TableRowColumn>
											<TableRowColumn>
												<IconButton onClick={this.handleClickViewCode}>
													<ActionCode />
												</IconButton>
											</TableRowColumn>
											<TableRowColumn className="hidden">{row.hibId}</TableRowColumn>
											<TableRowColumn className="hidden">{row.hibId}</TableRowColumn>
											<TableRowColumn className="hidden">{row.hibId}</TableRowColumn>
										</TableRow>
									))}
								</TableBody>
							</Table>
							<Popover
								open={this.state.open}
								style={{ marginTop: '10%', marginLeft: '35%', width: 'auto', height: 'auto' }}
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
											<EditorAce mode='javascript' />
										</Paper>
									</CardText>
									<CardActions className="footer">
										<RaisedButton
											label="Close"
											onClick={this.handleRequestClose}
											backgroundColor="#bd051f"
											labelColor="#FFFFFF" />
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