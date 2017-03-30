import React from 'react';
import ReactDom from 'react-dom';
import { Card, CardActions, CardHeader, CardMedia, CardTitle, CardText } from 'material-ui/Card';
import Divider from 'material-ui/Divider';
import TextField from 'material-ui/TextField';
import DatePicker from 'material-ui/DatePicker';
import DropDownMenu from 'material-ui/DropDownMenu';
import MenuItem from 'material-ui/MenuItem';
import {Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn} from 'material-ui/Table';
import Paper from 'material-ui/Paper';
import RaisedButton from 'material-ui/RaisedButton';


function handleChange(event, index, value) {
    this.setState({ value });
};

class AssignExercisesTab extends React.Component {

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
                        title="Append Exercises to Course"
                        />
                    <Divider />
                    <CardText>
                    <h4>Step 1: Select Exercise(s)</h4>
                    <Paper zDepth={2}>
                        <div>
                            <Table multiSelectable={true}>
                                <TableHeader>
                                    <TableRow>
                                        <TableHeaderColumn>Title</TableHeaderColumn>
                                        <TableHeaderColumn>Language</TableHeaderColumn>
                                        <TableHeaderColumn>Creation Date</TableHeaderColumn>
                                    </TableRow>
                                </TableHeader>
                                <TableBody>
                                    <TableRow>
                                        <TableRowColumn>Fibonacci</TableRowColumn>
                                        <TableRowColumn>Java</TableRowColumn>
                                        <TableRowColumn>24.11.2011</TableRowColumn>
                                    </TableRow>
                                    <TableRow>
                                        <TableRowColumn>Division</TableRowColumn>
                                        <TableRowColumn>JavaScript</TableRowColumn>
                                        <TableRowColumn>24.11.2011</TableRowColumn>
                                    </TableRow>
                                    <TableRow>
                                        <TableRowColumn>Sum</TableRowColumn>
                                        <TableRowColumn>JavaScript</TableRowColumn>
                                        <TableRowColumn>24.11.2011</TableRowColumn>
                                    </TableRow>
                                    <TableRow>
                                        <TableRowColumn>Sub</TableRowColumn>
                                        <TableRowColumn>Java</TableRowColumn>
                                        <TableRowColumn>24.11.2011</TableRowColumn>
                                    </TableRow>
                                </TableBody>
                            </Table>
                        </div>
                        </Paper>

                        <br/>
                        <h4>Step 2: Select Lecture</h4>
                        <Paper zDepth={2} style={{textAlign:"center"}}>
                        <DropDownMenu value={this.state.value} onChange={this.handleChange}>
                            <MenuItem value={1} primaryText="Webprogrammierung" />
                            <MenuItem value={2} primaryText="Datenbanken" />
                            <MenuItem value={3} primaryText="Programmieren 1" />
                        </DropDownMenu>
                        </Paper>

                        <br/>
                        <h4>Step 3: Select Deadline</h4>
                        <Paper zDepth={2} style={{textAlign:"center"}}>
                        <DatePicker floatingLabelText="Deadline" container="inline" mode="landscape" />
                        </Paper>

                        <br />
                        <RaisedButton label="Assign" />
                    </CardText>
                </Card>
            </div>
        );
    }
}

export default AssignExercisesTab;