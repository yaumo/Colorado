import React from 'react';
import ReactDom from 'react-dom';
import { Card, CardActions, CardHeader, CardMedia, CardTitle, CardText } from 'material-ui/Card';
import Divider from 'material-ui/Divider';
import Solution from './Solution.jsx';
import TextField from 'material-ui/TextField';
import DatePicker from 'material-ui/DatePicker';
import DropDownMenu from 'material-ui/DropDownMenu';
import MenuItem from 'material-ui/MenuItem';
import RaisedButton from 'material-ui/RaisedButton';
import Paper from 'material-ui/Paper';
import Fetch from 'react-fetch';


function handleClick(e) {
    fetch('http://localhost:8080/Excercise', {
        method: 'POST',
        mode: 'no-cors'
    }).then(function (response) {

    }).catch(function (err) {
        console.log(err)
    });
};

class ExercisesTab extends React.Component {

    constructor() {
        super();
        this.state = {
            open: true,
            value: 1
        };
    }
    render() {
        return (
            <div>
                <Card>
                    <CardHeader
                        title="New Exercise"
						className="loginheader"
                        />
                    <Divider />
                    <CardText className="loginbody">
                        <TextField
                            floatingLabelText="Title"
                            fullWidth={false}
                            />


                        <TextField
                            floatingLabelText="Exercise"
                            multiLine={true}
                            rows={3}
                            fullWidth={true}
                            />
                        <TextField
                            floatingLabelText="Youtube-Link"
                            fullWidth={true}
                            />
                        <br />
                        <br />
                        <h4>Pattern Solution</h4>
                        <Paper zDepth={4}>
                            <Solution />
                        </Paper>
                        <h4>Template</h4>
                        <Paper zDepth={4}>
                            <Solution rows={3} />
                        </Paper>
                        <br />

                        <h4>Testcases</h4>
                        <Paper zDepth={4}>
                            <div style={{ paddingLeft: "5%" }}>
                                Case 1
                                <br />
                                <TextField
                                    floatingLabelText="Input 1"
                                    fullWidth={false}
                                    />
                            </div>
                            <Divider />
                            <div style={{ paddingLeft: "5%" }}>
                                Case 2
                                <br />
                                <TextField
                                    floatingLabelText="Input 2"
                                    fullWidth={false}
                                    />
                            </div>
                            <Divider />
                            <div style={{ paddingLeft: "5%" }}>
                                Case 3
                                <br />
                                <TextField
                                    floatingLabelText="Input 3"
                                    fullWidth={false}
                                    />
                            </div>
                            <Divider />
                        </Paper>
                        <br />
                        <RaisedButton label="Create" onClick={handleClick} />
                    </CardText>
                </Card>
            </div>
        );
    }
}

export default ExercisesTab;