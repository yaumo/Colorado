import React from 'react';
import ReactDom from 'react-dom';

class OverviewTab extends React.Component {
    render() {
        return (
            <div>
                <h2>Tab One</h2>
                <p>
                    This is an Overview tab.
                </p>
                <p>
                    You can put any sort of HTML or react component in here. It even keeps the component state!
                </p>
            </div>
        );
    }
}

export default OverviewTab;