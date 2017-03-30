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


export class Docent extends React.Component {
    render() {
        return (
            <div>
                <NavBar />
                <Content />
            </div>
        );
    }
}

class Content extends React.Component {
    render() {
        return (
            <div id="content" className="content">
                <MuiThemeProvider muiTheme={getMuiTheme()}>
                    <div>
                        <Tabs>
                            <Tab label="Exercises" >
                                <ExercisesTab />
                            </Tab>
                            <Tab label="Assign Exercises" >
                                <AssignExercisesTab />
                            </Tab>
                            <Tab label="Overview" >
                                <OverviewTab />
                            </Tab>
                            <Tab
                                label="Lectures"
                                data-route="/home"
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