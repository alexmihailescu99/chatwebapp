import React from "react";
import axios from "axios";
import { backEndUrl } from "../App";

export default class LoginPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          username: "",
          password: "",
        };
        this.onChangeUserName = this.onChangeUserName.bind(this);
        this.onChangePassword = this.onChangePassword.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    componentDidMount() {
        if (localStorage.getItem("currentUser") !== "null") {
            window.location.href = "/";
        }
    }
    onChangeUserName(e) {
        this.setState({
            username: e.target.value
          });
    }

    onChangePassword(e) {
        this.setState({
            password: e.target.value
          });
    }

    onSubmit(e) {
        e.preventDefault();
        const object = JSON.stringify({
            username: this.state.username,
            password: this.state.password,
        });
        axios.post(backEndUrl + "/login", object)
        .then((res) => {
            //alert(res.data.token);
            // Store the JWT token(bad practice as it could get compromised, but it's just a proof of concept app)
            localStorage.setItem("jwtToken", res.data.token);
            localStorage.setItem("currentUser", this.state.username);
            alert("Welcome, " + this.state.username);
            window.location.href = "/";
        })
        .catch((err) => {
            if (err.response.status === 401) {
                alert("Wrong username or password");
                this.setState({
                    username: "",
                    password: ""
                });
                //window.location.reload();
            }
            //alert(this.state.username + " " + this.state.password);
        })
    }

    render() {
        return (
            <form onSubmit={this.onSubmit}>

                <h3>Login</h3>

                <div className="form-group">
                    <label>User</label>
                    <input value={this.state.username} onChange={this.onChangeUserName} id="username" type="text" className="form-control" placeholder="Enter username" />
                </div>

                <div className="form-group">
                    <label>Password</label>
                    <input value={this.state.password} onChange={this.onChangePassword} id="password" type="password" className="form-control" placeholder="Enter password" />
                </div>

                <button type="submit" className="btn btn-dark btn-lg btn-block">Log in</button>
                <p className="forgot-password text-right">
                    Don't have an <a href="/register">account</a>?
                </p>
            </form>
        );
    }
}