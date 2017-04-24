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
import Dialog from 'material-ui/Dialog';

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
            opendialog: false,
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
			selectedexerciseid: '',
			userlist: [],
			dialog: [],
			overviewlist: [],
			allUsers: ''
		};
		this.handleChangeLecture = this.handleChangeLecture.bind(this);
		this.handleChangeCourse = this.handleChangeCourse.bind(this);
		this.handleChangeExercise = this.handleChangeExercise.bind(this);
		this.handleClickViewCode = this.handleClickViewCode.bind(this);
		this.handleRequestClose = this.handleRequestClose.bind(this);
		this.handleClickSearch = this.handleClickSearch.bind(this);
		this.handleOpenDialog = this.handleOpenDialog.bind(this);
        this.handleCloseDialog = this.handleCloseDialog.bind(this);
	}

	componentDidMount() {
		$.ajax({
			url: "http://localhost:8181/docent/courses",
			dataType: 'json',
			method: 'GET',
			success: function (courses) {
				coursesJSON = courses;
				if (courselist.length === 0) {
					for (var i = 0; i < coursesJSON.length; i++) {
						courselist.push(<MenuItem value={i} key={i} primaryText={coursesJSON[i].title} />);
						courseids.push(coursesJSON[i].id);
						this.setState({ selectedcourseid: courseids[0] });
					}
				}
				if (lecturelist.length === 0) {
					lecturelist.push(<MenuItem value={0} key={0} primaryText={'All Lectures'} />);
					lectureids.push('');
					for (var j = 1; j <= coursesJSON[0].lectures.length; j++) {
						lecturelist.push(<MenuItem value={j} key={j} primaryText={coursesJSON[0].lectures[j - 1].title} />);
						lectureids.push(coursesJSON[0].lectures[j - 1].id);
						this.setState({ selectedlectureid: lectureids[0] });
					}
				}
				if (exerciselist.length === 0) {
					exerciselist.push(<MenuItem value={0} key={0} primaryText={'Select a Lecture'} />);
					if (coursesJSON[0].lectures[0]) {
						if (coursesJSON[0].lectures[0].exercises) {
							for (var j = 1; j <= coursesJSON[0].lectures[0].exercises.length; j++) {
								exerciselist.push(<MenuItem value={j} key={j} primaryText={coursesJSON[0].lectures[0].exercises[1 - j].title} />);
							}
						}
					}
					exerciseids.push('');
					this.setState({ selectedexerciseid: exerciseids[0] });
				}
				this.setState({ courselist: courselist });
				this.setState({ lecturelist: lecturelist });
				this.setState({ exerciselist: exerciselist });
				this.setState({ selectedLecture: 0 });
				this.setState({ selectedExercise: 0 });
				this.setState({ disabledDropDownExercise: true });
			}.bind(this)
		});
	}


	handleChangeCourse(event, index, value) {
		var countLectures = lecturelist.length;
		var countExercises = exerciselist.length;

		for (var i = 0; i < countLectures; i++) {
			lecturelist.pop();
			lectureids.pop();
		}
		for (var i = 0; i < countExercises; i++) {
			exerciselist.pop();
			exerciseids.pop();
		}
		lecturelist.push(<MenuItem value={0} key={0} primaryText={'All Lectures'} />);
		lectureids.push('');
		for (var j = 1; j <= coursesJSON[value].lectures.length; j++) {
			lecturelist.push(<MenuItem value={j} key={j} primaryText={coursesJSON[value].lectures[j - 1].title} />);
			lectureids.push(coursesJSON[value].lectures[j - 1].id);
			this.setState({ selectedlectureid: lectureids[0] });
		}

		exerciselist.push(<MenuItem value={0} key={0} primaryText={'Select a Lecture'} />);
		exerciseids.push('');
		this.setState({ selectedexerciseid: exerciseids[0] });

		this.setState({ selectedcourseid: courseids[value] });
		this.setState({ selectedCourse: value });
		this.setState({ selectedLecture: 0 });
		this.setState({ selectedExercise: 0 });
	}

	handleChangeLecture(event, index, value) {
		var count = exerciselist.length;
		for (var i = 0; i < count; i++) {
			exerciselist.pop();
			exerciseids.pop();
		}
		if (value != 0) {
			exerciselist.push(<MenuItem value={0} key={0} primaryText={'All Exercises'} />);
			exerciseids.push('');
			this.setState({ disabledDropDownExercise: false });
			for (var k = 1; k <= coursesJSON[this.state.selectedCourse].lectures[value - 1].exercises.length; k++) {
				exerciselist.push(<MenuItem value={k} key={k} primaryText={coursesJSON[this.state.selectedCourse].lectures[value - 1].exercises[k - 1].title} />);
				exerciseids.push(coursesJSON[this.state.selectedCourse].lectures[value - 1].exercises[k - 1].id)
			}
		}
		else {
			exerciselist.push(<MenuItem value={0} key={0} primaryText={'Select a Lecture'} />);
			this.setState({ disabledDropDownExercise: true });
			exerciseids.push('');
		}
		this.setState({ selectedLecture: value });
		this.setState({ selectedExercise: 0 });
		this.setState({ selectedlectureid: lectureids[value] });
		this.setState({ selectedexerciseid: exerciseids[0] });
	}

	handleChangeExercise(event, index, value) {
		this.setState({ selectedExercise: value });
		this.setState({ selectedexerciseid: exerciseids[value] });
	}

	handleOpenDialog(event, index, value) {
        this.setState({ opendialog: true });
    }
    handleCloseDialog(event, index, value) {
        this.setState({ opendialog: false });
    }

	handleClickViewCode(event, index, value) {
		var lectureID = this.state.selectedlectureid;
		var exerciseID = this.state.selectedexerciseid;
		var userID = event.currentTarget.parentElement.parentElement.cells[4].innerText;

		$.ajax({
			url: "http://localhost:8181/docent/solution",
			dataType: 'json',
			method: 'GET',
			data: {
				"excId": exerciseID,
				"usrId": userID
			},
			success: function (solution) {
				solutionJSON = solution;
				//lecture = event.currentTarget.parentElement.parentElement.cells[1].innerText;
				//exercise = event.currentTarget.parentElement.parentElement.cells[2].innerText;
				name = event.currentTarget.parentElement.parentElement.cells[0].innerText;

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
		var lectureID = this.state.selectedlectureid;
		var exerciseID = this.state.selectedexerciseid;

		if (lectureID === "") {
			this.setState({
				opendialog: true,
				dialog: "Please select a Lecture"
			});
		}
		else if (exerciseID === "") {
			this.setState({
				opendialog: true,
				dialog: "Please select an Exercise"
			});
		}
		else {
			$.ajax({
				url: "http://localhost:8181/docent/solutions",
				dataType: 'json',
				method: 'GET',
				data: {
					"lectureId": lectureID,
					"exerciseId": exerciseID
				},
				success: function (solutions) {
					solutionsJSON = solutions;
					for (var i = 0; i < solutionsJSON.length; i++) {
						solutionsJSON[i].username = solutionsJSON[i].owner.username;
						solutionsJSON[i].id = solutionsJSON[i].owner.id;
						solutionsJSON[i].correct = solutionsJSON[i].correct.toString();
					}
					this.setState({
						overviewlist: solutionsJSON
					});
				}.bind(this),
				error: function (error) {
					//handle error
				}.bind(this)
			});
		}
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
										<TableHeaderColumn>Name</TableHeaderColumn>
										<TableHeaderColumn>Status</TableHeaderColumn>
										<TableHeaderColumn>View Code</TableHeaderColumn>
										<TableHeaderColumn >exerciseID</TableHeaderColumn>
										<TableHeaderColumn >userID</TableHeaderColumn>
										<TableHeaderColumn >lectureID</TableHeaderColumn>
									</TableRow>
								</TableHeader>
								<TableBody displayRowCheckbox={false} style={{ background: "#d1d1d1" }}>
									{this.state.overviewlist.map((row, index) => (
										<TableRow key={index} selected={row.selected}>
											<TableRowColumn>{row.username}</TableRowColumn>
											<TableRowColumn>{row.correct}</TableRowColumn>
											<TableRowColumn>
												<IconButton onClick={this.handleClickViewCode}>
													<ActionCode />
												</IconButton>
											</TableRowColumn>
											<TableRowColumn >{this.state.selectedexerciseid}</TableRowColumn>
											<TableRowColumn >{row.id}</TableRowColumn>
											<TableRowColumn >{this.state.selectedlectureid}</TableRowColumn>
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
						<Dialog
                            title="Information"
                            modal={false}
                            open={this.state.opendialog}
                            onRequestClose={this.handleCloseDialog}>
                            {this.state.dialog}
                        </Dialog>
					</CardActions>
				</Card>
			</div>
		);
	}
}

export default OverviewTab;