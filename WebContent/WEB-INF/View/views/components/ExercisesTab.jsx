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
    fetch('http://localhost:8080/excercise', {
        method: 'POST',
        mode: 'no-cors'
    }).then(function (response) {

    }).catch(function (err) {
        console.log(err)
    });
};

function handleChange(event, index, value) {
    this.setState({ value });
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
                <Card className="card">

                    <Divider />
                    <CardText className="loginbody">
                        <TextField
                            floatingLabelText="Title"
                            fullWidth={false}
                            />
                        <DropDownMenu className="language" value={this.state.value} onChange={this.handleChange}>
                            <MenuItem value={1} primaryText="JavaScript" />
                            <MenuItem value={2} primaryText="Java" />
                        </DropDownMenu>
                        <TextField
                            floatingLabelText="Description"
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
                                <TextField
                                    floatingLabelText="Case 1: Input"
                                    fullWidth={false}
                                    />
                            </div>
                            <Divider />
                            <div style={{ paddingLeft: "5%" }}>
                                <TextField
                                    floatingLabelText="Case 3: Input"
                                    fullWidth={false}
                                    />
                            </div>
                            <Divider />
                            <div style={{ paddingLeft: "5%" }}>
                                <TextField
                                    floatingLabelText="Case 3: Input"
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