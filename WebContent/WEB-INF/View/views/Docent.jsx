import React from 'react';
import ReactDom from 'react-dom';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import darkBaseTheme from 'material-ui/styles/baseThemes/darkBaseTheme';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import NavBar from './components/NavBar.jsx';
import Solution from './components/Solution.jsx';
import { Tabs, Tab } from 'material-ui/Tabs';
import Slider from 'material-ui/Slider';
import ExercisesTab from './components/ExercisesTab.jsx';
import AssignExercisesTab from './components/AssignExercisesTab.jsx';
import OverviewTab from './components/OverviewTab.jsx';
import LecturesTab from './components/LecturesTab.jsx';
import DocentBar from './components/DocentBar.jsx';

var courseJSON;
const courselist = [];
const lecturelist = [];
const exerciselist = [];

export class Docent extends React.Component {
    render() {
        return (
            <div>
                <DocentBar />
                <Content />
            </div>
        );
    }
}

class Content extends React.Component {
    constructor() {
        super();
    }

    /*componentWillMount() {
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
            console.log(courses.length);
            courseJSON = courses;
            lecturelist.push(<MenuItem value={0} key={0} primaryText={'All Lectures'} />);
            for (var i = 0; i < course.length; i++) {
                courselist.push(<MenuItem value={i} key={i} primaryText={course[i].title} />);
            }
            for (var j = 1; j <= course[0].lectures.length; j++) {
                lecturelist.push(<MenuItem value={j} key={j} primaryText={course[0].lectures[j - 1].title} />);
            }
        }).catch(function (err) {
            console.log(err);
        });
    }*/




render() {
    return (
        <div id="contentDocent" className="contentDocent">
            <MuiThemeProvider muiTheme={getMuiTheme({
                datePicker: { selectColor: '#bd051f' },
                flatButton: { primaryTextColor: '#bd051f' }
            }
            )} >
                <div>
                    <Tabs>
                        <Tab label="New Exercise" style={{ 'backgroundColor': '#bd051f' }}>
                            <ExercisesTab />
                        </Tab>
                        <Tab label="Append Exercises" style={{ 'backgroundColor': '#bd051f' }}>
                            <AssignExercisesTab />
                        </Tab>
                        <Tab label="Overview" style={{ 'backgroundColor': '#bd051f' }}>
                            <OverviewTab />
                        </Tab>
                        <Tab
                            style={{ 'backgroundColor': '#bd051f' }}
                            label="New Lecture"
                        >
                            <LecturesTab />
                        </Tab>
                    </Tabs>
                </div>
            </MuiThemeProvider>
        </div>
    );
}
}

export default Docent;