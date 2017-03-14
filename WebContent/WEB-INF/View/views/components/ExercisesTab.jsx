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


function handleClick(e){
    console.log("click", e);
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
                        />
                    <Divider />
                    <CardText>
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
                        
                        <br />
                        <br />
                        <h4>Template</h4>
                        <Paper zDepth={4}>
                        <Solution />
                        </Paper>
                        <br/>
                        <Paper zDepth={4}>
                        <h4>Testcases</h4>
                        <Divider />
                        <div>
                            Case 1
                            <TextField
                                floatingLabelText="Input 1"
                                fullWidth={false}
                                multiLine={true}
                                rows={3}
                            />
                            &nbsp;
                            &nbsp;
                            <TextField
                                floatingLabelText="Output 1"
                                fullWidth={false}
                                multiLine={true}
                                rows={3}
                            />
                        </div>
                        <Divider />
                        <div>
                        Case 2
                            <TextField
                                floatingLabelText="Input 2"
                                fullWidth={false}
                                multiLine={true}
                                rows={3}
                            />
                            &nbsp;
                            &nbsp;
                            <TextField
                                floatingLabelText="Output 2"
                                fullWidth={false}
                                multiLine={true}
                                rows={3}
                            />
                        </div>
                        <Divider />
                        <div>
                        Case 3
                            <TextField
                                floatingLabelText="Input 3"
                                fullWidth={false}
                                multiLine={true}
                                rows={3}
                                className="abcName"
                            />
                            &nbsp;
                            &nbsp;
                            <TextField
                                floatingLabelText="Output 3"
                                fullWidth={false}
                                multiLine={true}
                                rows={3}
                            />
                        </div>
                        <Divider />
                        </Paper>
                        <br/>
                        <RaisedButton label="Create" onClick={handleClick} />
                    </CardText>
                </Card>
            </div>
        );
    }
}

export default ExercisesTab;