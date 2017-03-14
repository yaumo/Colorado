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
                    <Paper zDepth={4}>
                        <div>
                        
                            <Table>
                                <TableHeader>
                                    <TableRow>
                                        <TableHeaderColumn>ID</TableHeaderColumn>
                                        <TableHeaderColumn>Name</TableHeaderColumn>
                                        <TableHeaderColumn>Status</TableHeaderColumn>
                                    </TableRow>
                                </TableHeader>
                                <TableBody>
                                    <TableRow>
                                        <TableRowColumn>1</TableRowColumn>
                                        <TableRowColumn>John Smith</TableRowColumn>
                                        <TableRowColumn>Employed</TableRowColumn>
                                    </TableRow>
                                    <TableRow>
                                        <TableRowColumn>2</TableRowColumn>
                                        <TableRowColumn>Randal White</TableRowColumn>
                                        <TableRowColumn>Unemployed</TableRowColumn>
                                    </TableRow>
                                    <TableRow>
                                        <TableRowColumn>3</TableRowColumn>
                                        <TableRowColumn>Stephanie Sanders</TableRowColumn>
                                        <TableRowColumn>Employed</TableRowColumn>
                                    </TableRow>
                                    <TableRow>
                                        <TableRowColumn>4</TableRowColumn>
                                        <TableRowColumn>Steve Brown</TableRowColumn>
                                        <TableRowColumn>Employed</TableRowColumn>
                                    </TableRow>
                                </TableBody>
                            </Table>
                            
                        </div>
                        </Paper>
                        <DropDownMenu value={this.state.value} onChange={this.handleChange}>
                            <MenuItem value={1} primaryText="Webprogrammierung" />
                            <MenuItem value={2} primaryText="Datenbanken" />
                            <MenuItem value={3} primaryText="Programmieren 1" />
                        </DropDownMenu>
                        <DatePicker floatingLabelText="Deadline" container="inline" mode="landscape" />
                        <br />
                    </CardText>
                </Card>
            </div>
        );
    }
}

export default AssignExercisesTab;