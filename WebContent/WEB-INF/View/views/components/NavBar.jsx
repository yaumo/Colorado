import React from 'react';
import MuiThemeProvider from 'material-ui/styles/MuiThemeProvider';
import Drawer from 'material-ui/Drawer';
import MenuItem from 'material-ui/MenuItem';
import darkBaseTheme from 'material-ui/styles/baseThemes/darkBaseTheme';
import getMuiTheme from 'material-ui/styles/getMuiTheme';
import AppBar from 'material-ui/AppBar';
import Divider from 'material-ui/Divider';
import RaisedButton from 'material-ui/RaisedButton';
import DropDownMenu from 'material-ui/DropDownMenu';


function handleChange(event, index, value){
    this.setState({value});
};

class NavBar extends React.Component {
    constructor() {
        super();
        this.state = {
            open: true,
            value: 1
        };
    }

    render() {
        return (
            <div style={{ 'margin': '0' }}>
                <MuiThemeProvider muiTheme={getMuiTheme(darkBaseTheme)}>
                    <div>
                        <AppBar title="Colorado" style={{ 'position': 'fixed'}} />

                        <Drawer
                            id="drawer"
                            docked={true}
                            open={this.state.open}
                            onRequestChange={(open) => this.setState({ open })}
                            containerStyle={{ 'position': 'fixed', 'margin': '0', 'top': '64px', height: 'calc(100% - 64px)' }}
                            className= ""
                            >
                            <DropDownMenu value={this.state.value} onChange={this.handleChange}>
                                <MenuItem value={1} primaryText="Webprogrammierung" />
                                <MenuItem value={2} primaryText="Datenbanken" />
                                <MenuItem value={3} primaryText="Programmieren 1" />
                            </DropDownMenu>
                            <Divider />
                            <MenuItem>Exercise 1</MenuItem>
                            <Divider />
                            <MenuItem>Exercise 2</MenuItem>
                            <Divider />
                        </Drawer>
                    </div>
                </MuiThemeProvider>
            </div>
        );
    }
}

export default NavBar;