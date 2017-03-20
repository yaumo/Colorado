import React from 'react';
import ReactDOM from 'react-dom';
import {render} from "react-dom";
import { Router, Route, browserHistory} from 'react-router';
import {Login} from './views/Login.jsx';
import {Exercise} from './views/Exercise.jsx';
import {Docent} from './views/Docent.jsx';
import NavBar from './views/components/NavBar.jsx';
import Solution from './views/components/Solution.jsx';
import ExercisesTab from './views/components/ExercisesTab.jsx';
import OverviewTab from './views/components/OverviewTab.jsx';



//ReactDOM.render(<App />, document.getElementById('app'));
ReactDOM.render(<Docent />, document.getElementById('app'));

class App extends React.Component {
    render() {
        return (
            <Router history={browserHistory}>
				<Route path={"/"} component={Login} />
                <Route path={"exercise"} component={Exercise} />
				<Route path={"docent"} component={Docent} />	 
            </Router>
        );
    }
}
render(<App />, window.document.getElementById('app'));