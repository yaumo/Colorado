import React from 'react';
import ReactDom from 'react-dom';
import { Card, CardActions, CardHeader, CardMedia, CardTitle, CardText } from 'material-ui/Card';
import Divider from 'material-ui/Divider';
import TextField from 'material-ui/TextField';
import DatePicker from 'material-ui/DatePicker';
import DropDownMenu from 'material-ui/DropDownMenu';
import MenuItem from 'material-ui/MenuItem';
import { Table, TableBody, TableHeader, TableHeaderColumn, TableRow, TableRowColumn } from 'material-ui/Table';
import Paper from 'material-ui/Paper';
import RaisedButton from 'material-ui/RaisedButton';


function handleChange(event, index, value) {
    this.setState({ value });
};

function handleClick(e) {
    fetch('http://localhost:8080/', {
        method: 'POST',
        mode: 'no-cors',
        credentials: 'same-origin',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({
            objectClass: 'exercise',
            crud: '1',
            title: 'FrontEnd',
            description: 'Test 1234',
            code: 'bam bam baaaaam'
        })
    }).then(function (response) {

    }).catch(function (err) {
        console.log(err)
    });
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
                    <Divider />
                    <CardText className="loginbody">
                        <h4>Step 1: Select Exercise(s)</h4>
                        <Paper zDepth={2} className="paper">
                            <div>
                                <Table multiSelectable={true} className="paper" >
                                    <TableHeader>
                                        <TableRow className="paper">
                                            <TableHeaderColumn>Title</TableHeaderColumn>
                                            <TableHeaderColumn>Language</TableHeaderColumn>
                                            <TableHeaderColumn>Creation Date</TableHeaderColumn>
                                        </TableRow>
                                    </TableHeader>
                                    <TableBody deselectOnClickaway={false} className="paper">
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

                        <br />
                        <h4>Step 2: Select Lecture</h4>
                        <Paper zDepth={2} style={{ textAlign: "center", background: "#d1d1d1" }}>
                            <DropDownMenu value={this.state.value} onChange={this.handleChange}>
                                <MenuItem value={1} primaryText="WWI14SEA" />
                                <MenuItem value={2} primaryText="WWI14SEB" />
                                <MenuItem value={3} primaryText="WWI16AMA" />
                            </DropDownMenu>

                            <DropDownMenu value={this.state.value} onChange={this.handleChange}>
                                <MenuItem value={1} primaryText="Webprogrammierung" />
                                <MenuItem value={2} primaryText="Datenbanken" />
                                <MenuItem value={3} primaryText="Programmieren 1" />
                            </DropDownMenu>
                        </Paper>

                        <br />
                        <h4>Step 3: Select Deadline</h4>
                        <Paper zDepth={2} style={{ textAlign: "center", background: "#d1d1d1" }}>
                            <DatePicker floatingLabelText="Deadline" mode="landscape"/>
                        </Paper>
                    </CardText>
                    <CardActions className="footer">
                        <RaisedButton label="Assign"onClick={handleClick} />
                    </CardActions>
                </Card>
            </div>
        );
    }
}

export default AssignExercisesTab;