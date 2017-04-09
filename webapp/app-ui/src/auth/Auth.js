import React, {Component} from 'react';

import * as firebase from 'firebase';
import { MenuItem } from 'react-bootstrap';

class FirebaseLogin extends Component {

    constructor(props) {
        super(props);
        this.state = {"loggedIn": false};
    }

    componentDidMount() {
        firebase.auth().onAuthStateChanged(firebaseUser => {
                if (firebaseUser) {
                    this.setState({"loggedIn": true});
                    console.log('AuthState Changed - Signed In');
                } else {
                    this.setState({"loggedIn": false});
                    console.log('AuthState Changed - Signed Out');
                }
            }
        );
        this.setState({"loggedIn": false});
    }

    signOut() {
        firebase.auth().signOut();
        console.log('Signing Out');
    }

    signIn() {
        firebase.auth().signInAnonymously().catch(function(error) {
            //https://firebase.google.com/docs/auth/web/anonymous-auth#authenticate-with-firebase-anonymously
            // Handle Errors here.
            var errorCode = error.code;
            var errorMessage = error.message;
        });
    }

    render() {
        let loginStatusButton = null;
        if (this.state.loggedIn) {
            // loginStatusButton = (<div onClick={this.signOut.bind(this)}>Sign Out</div>);
            loginStatusButton = <MenuItem eventKey={3.3} onClick={this.signOut.bind(this)}>Sign Out</MenuItem>;
        } else {
            loginStatusButton = <MenuItem onClick={this.signIn.bind(this)}>Sign In</MenuItem>;
        }
        return loginStatusButton;
    }
}

export default FirebaseLogin;
