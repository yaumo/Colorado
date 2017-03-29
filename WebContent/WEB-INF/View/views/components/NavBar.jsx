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
import {white, red500, black, green} from 'material-ui/styles/colors';
import ActionPerson from 'material-ui/svg-icons/action/account-circle';
import ActionCheck from 'material-ui/svg-icons/action/check-circle';
import IconButton from 'material-ui/IconButton';
import IconMenu from 'material-ui/IconMenu';

const personStyles = {
  marginRight: 24,
  marginTop: 12,
};

const iconStyles = {
  marginRight: 12,
};

function handleChange(event, index, value){
    console.log("click");
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
                <MuiThemeProvider muiTheme={getMuiTheme()}>
                    <div>
                        <AppBar 
                            title="Colorado"
                            style={{ 'position': 'fixed'}} 
                            iconElementRight={
                                <IconMenu
                                    iconButtonElement={<IconButton>
                                                        <ActionPerson style={personStyles} color={white} hoverColor={black}/>
                                                    </IconButton>}
                                    targetOrigin={{horizontal: 'right', vertical: 'top'}}
                                    anchorOrigin={{horizontal: 'right', vertical: 'top'}}
                                    >
                                    <MenuItem primaryText="Settings" />
                                    <MenuItem primaryText="Sign out" />
                                </IconMenu>      
                                }
                            />

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
                            <MenuItem><div><ActionCheck style={iconStyles} color={green}/>Exercise 1</div></MenuItem>
                            <Divider />
                            <MenuItem>Exercise 2</MenuItem>
                        </Drawer>
                    </div>
                </MuiThemeProvider>
            </div>
        );
    }
}

export default NavBar;