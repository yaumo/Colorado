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


const tableData = [
    {
        name: 'John Smith',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'Done',
    },
    {
        name: 'Randal White',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'In Progress',
    },
    {
        name: 'Stephanie Sanders',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'In Progress',
    },
    {
        name: 'Steve Brown',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'In Progress',
    },
    {
        name: 'Joyce Whitten',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'In Progress',
    },
    {
        name: 'Samuel Roberts',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'Done',
    },
    {
        name: 'Adam Moore',
        exercise: 'Fibonacci',
        lecture: 'Webprogrammierung',
        status: 'Done',
    },
];

class OverviewTab extends React.Component {
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
                    <Divider />
                    <CardText className="loginbody">
                        <Paper zDepth={4}>
                            <Table selectable={false}
                            >
                                <TableHeader displaySelectAll={false} adjustForCheckbox={false}>
                                    <TableRow>
                                        <TableHeaderColumn colSpan="6" style={{ textAlign: 'center' }}>
                                            <DropDownMenu value={this.state.value} onChange={this.handleChange}>
                                                <MenuItem value={1} primaryText="WWI14SEA" />
                                                <MenuItem value={2} primaryText="WWI14AMA" />
                                                <MenuItem value={3} primaryText="WWI16SEB" />
                                            </DropDownMenu>
                                            <DropDownMenu value={this.state.value} onChange={this.handleChange}>
                                                <MenuItem value={1} primaryText="All Lectures" />
                                                <MenuItem value={2} primaryText="Datenbanken" />
                                                <MenuItem value={3} primaryText="Programmieren 1" />
                                                <MenuItem value={4} primaryText="Webprogrammierung" />
                                            </DropDownMenu>
                                            <DropDownMenu value={this.state.value} onChange={this.handleChange}>
                                                <MenuItem value={1} primaryText="All Exercises" />
                                                <MenuItem value={2} primaryText="Sum" />
                                                <MenuItem value={3} primaryText="Sub" />
                                                <MenuItem value={3} primaryText="Fibonacci" />
                                            </DropDownMenu>
                                            <RaisedButton label="Apply" />
                                        </TableHeaderColumn>
                                    </TableRow>
                                    <TableRow>
                                        <TableHeaderColumn>Course</TableHeaderColumn>
                                        <TableHeaderColumn>Lecture</TableHeaderColumn>
                                        <TableHeaderColumn>Exercise</TableHeaderColumn>
                                        <TableHeaderColumn>Name</TableHeaderColumn>
                                        <TableHeaderColumn>Status</TableHeaderColumn>
                                        <TableHeaderColumn>View Code</TableHeaderColumn>
                                    </TableRow>
                                </TableHeader>
                                <TableBody displayRowCheckbox={false}>
                                    {tableData.map((row, index) => (
                                        <TableRow key={index} selected={row.selected}>
                                            <TableRowColumn>{"WWI14SEA"}</TableRowColumn>
                                            <TableRowColumn>{row.lecture}</TableRowColumn>
                                            <TableRowColumn>{row.exercise}</TableRowColumn>
                                            <TableRowColumn>{row.name}</TableRowColumn>
                                            <TableRowColumn>{row.status}</TableRowColumn>
                                            <TableRowColumn><RaisedButton label="?" /></TableRowColumn>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>
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