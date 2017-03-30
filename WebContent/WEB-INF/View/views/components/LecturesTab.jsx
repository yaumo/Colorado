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


const tableData = [
    {
        name: 'John Smith',
        mail: 'd12345@docent.dhbw-mannheim.de',
    },
    {
        name: 'Randal White',
        mail: 'd12345@docent.dhbw-mannheim.de',
    },
    {
        name: 'Stephanie Sanders',
        mail: 'd12345@docent.dhbw-mannheim.de',
    },
    {
        name: 'Steve Brown',
        mail: 'd12345@docent.dhbw-mannheim.de',
    },
    {
        name: 'Joyce Whitten',
        mail: 'd12345@docent.dhbw-mannheim.de',
    },
    {
        name: 'Samuel Roberts',
        mail: 'd12345@docent.dhbw-mannheim.de',
    },
    {
        name: 'Adam Moore',
        mail: 'd12345@docent.dhbw-mannheim.de',
    },
];


function handleChange(event, index, value) {
    this.setState({ value });
};

class LecturesTab extends React.Component {

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
                    <h4>Step 1: Select Curse</h4>
                    <Paper zDepth={2} style={{textAlign:"center"}}>
                        <div>
                           <DropDownMenu value={this.state.value} onChange={this.handleChange}>
                            <MenuItem value={1} primaryText="WWI14SEA" />
                            <MenuItem value={2} primaryText="WWI14AMA" />
                            <MenuItem value={3} primaryText="WWI16SEB" />
                        </DropDownMenu>
                        </div>
                        </Paper>

                        <br/>
                        <h4>Step 2: Name Lecture</h4>
                        <Paper zDepth={2} style={{textAlign:"center"}}>
                        <TextField
                            floatingLabelText="Lecture Name"
                            fullWidth={false}
                            />
                        </Paper>

                        <br/>
                        <h4>Step 3: Select Tutors</h4>
                        <Paper zDepth={2} style={{textAlign:"center"}}>
                            <Table multiSelectable={true}
                                >
                                <TableHeader displaySelectAll={false}>
                                    <TableRow>
                                        <TableHeaderColumn>Name</TableHeaderColumn>
                                        <TableHeaderColumn>E-Mail</TableHeaderColumn>
                                    </TableRow>
                                </TableHeader>
                                <TableBody>
                                    {tableData.map((row, index) => (
                                        <TableRow key={index} selected={row.selected}>
                                            <TableRowColumn>{row.name}</TableRowColumn>
                                            <TableRowColumn>{row.mail}</TableRowColumn>
                                        </TableRow>
                                    ))}
                                </TableBody>
                            </Table>

                        </Paper>

                        <br />
                        <RaisedButton label="Create" />
                    </CardText>
                </Card>
            </div>
        );
    }
}

export default LecturesTab;