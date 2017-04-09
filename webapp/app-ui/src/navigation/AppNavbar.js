/**
 * Created by Siva on 4/8/2017.
 */
import React, { Component } from 'react';
import { Navbar, Nav, NavItem, NavDropdown, MenuItem, Glyphicon } from 'react-bootstrap';
import FirebaseLogin from "../auth/Auth";

class AppNavbar extends Component {
    render() {
        return (
            <Navbar inverse collapseOnSelect fluid staticTop default >
                <Navbar.Header>
                    <Navbar.Brand>
                            Paying Guest Portal
                    </Navbar.Brand>
                    <Navbar.Toggle />
                </Navbar.Header>
                <Navbar.Collapse>
                    <Nav>
                        <NavDropdown eventKey={1} title="PG Admin" id="basic-nav-dropdown">
                            <MenuItem eventKey={1.1}>Check In</MenuItem>
                            <MenuItem divider />
                            <MenuItem eventKey={1.2}>Check Out</MenuItem>
                        </NavDropdown>
                        <NavItem eventKey={2} href="#">Guest</NavItem>
                        <NavDropdown eventKey={3} title="Portal Admin" id="basic-nav-dropdown">
                            <MenuItem eventKey={3.1}>Register PG</MenuItem>
                            <MenuItem divider />
                            <MenuItem eventKey={3.2}>DeActivate PG</MenuItem>
                        </NavDropdown>
                    </Nav>
                    <Nav pullRight>
                        <NavItem eventKey={1} > </NavItem>
                        <NavDropdown eventKey={3} title={ <Glyphicon glyph="user" /> } id="basic-nav-dropdown">
                            <FirebaseLogin />
                            <MenuItem divider />
                            <MenuItem eventKey={3.3}>Logout</MenuItem>
                        </NavDropdown>
                    </Nav>
                </Navbar.Collapse>
            </Navbar>
        );
    }
}

export default AppNavbar;