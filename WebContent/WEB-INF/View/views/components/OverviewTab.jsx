import React from 'react';
import ReactDom from 'react-dom';
import { Card, CardActions, CardHeader, CardMedia, CardTitle, CardText } from 'material-ui/Card';
import Divider from 'material-ui/Divider';
import { Table, TableBody, TableFooter, TableHeader, TableHeaderColumn, TableRow, TableRowColumn }
    from 'material-ui/Table';
import Paper from 'material-ui/Paper';


const tableData = [
    {
        name: 'John Smith',
        status: 'Employed',
    },
    {
        name: 'Randal White',
        status: 'Unemployed',
    },
    {
        name: 'Stephanie Sanders',
        status: 'Employed',
    },
    {
        name: 'Steve Brown',
        status: 'Employed',
    },
    {
        name: 'Joyce Whitten',
        status: 'Employed',
    },
    {
        name: 'Samuel Roberts',
        status: 'Employed',
    },
    {
        name: 'Adam Moore',
        status: 'Employed',
    },
];

class OverviewTab extends React.Component {
    render() {
        return (
            <div>
                <Card>
                    <CardHeader
                        title="Overview"
                        />
                    <Divider />
                    <CardText>
                    <Paper zDepth={4}>
                        <Table
                            >
                            <TableHeader>
                                <TableRow>
                                    <TableHeaderColumn colSpan="3" style={{ textAlign: 'center' }}>
                                        Super Header
                                    </TableHeaderColumn>
                                </TableRow>
                                <TableRow>
                                    <TableHeaderColumn>ID</TableHeaderColumn>
                                    <TableHeaderColumn>Name</TableHeaderColumn>
                                    <TableHeaderColumn>Status</TableHeaderColumn>
                                </TableRow>
                            </TableHeader>
                            <TableBody>
                                {tableData.map((row, index) => (
                                    <TableRow key={index} selected={row.selected}>
                                        <TableRowColumn>{index}</TableRowColumn>
                                        <TableRowColumn>{row.name}</TableRowColumn>
                                        <TableRowColumn>{row.status}</TableRowColumn>
                                    </TableRow>
                                ))}
                            </TableBody>
                        </Table>
                        </Paper>
                    </CardText>
                </Card>
            </div>
        );
    }
}

export default OverviewTab;