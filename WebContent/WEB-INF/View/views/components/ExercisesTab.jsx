import React from 'react';
import ReactDom from 'react-dom';
import { Card, CardActions, CardHeader, CardMedia, CardTitle, CardText } from 'material-ui/Card';
import Divider from 'material-ui/Divider';
import Solution from './Solution.jsx';
import TextField from 'material-ui/TextField';
import DatePicker from 'material-ui/DatePicker';
import DropDownMenu from 'material-ui/DropDownMenu';
import MenuItem from 'material-ui/MenuItem';


function handleChange(event, index, value){
    this.setState({value});
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
                        /><br />
                        <br />
                        <h4>Solution:</h4>
                        <Solution />
                    </CardText>
                </Card>
                <br />
                <Card>
                    <CardHeader
                        title="Append Exercises to Course"
                        />
                    <Divider />
                    <CardText>
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

export default ExercisesTab;