import React from "react";
import axios from "axios";
import { backEndUrl } from "../App";

export default class RegisterPage extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
          username: "",
          password: "",
          fullName: ""
        };
        this.onChangeUserName = this.onChangeUserName.bind(this);
        this.onChangePassword = this.onChangePassword.bind(this);
        this.onChangeFullName = this.onChangeFullName.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
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

    onChangeFullName(e) {
        this.setState({
            fullName: e.target.value
          });
    }

    onSubmit(e) {
        axios.post(backEndUrl + "/user/register", {
            username: this.state.username,
            password: this.state.password,
            fullName: this.state.fullName,
            status : "Now available!"
        })
        .then((res) => {
            window.location.href = "/login";
        })
        .catch((err) => {
            alert(err);
        })
    }

    render() {
        return (
            <form onSubmit={this.onSubmit}>

                <h3>Register</h3>

                <div className="form-group">
                    <label>User</label>
                    <input value={this.state.username} onChange={this.onChangeUserName} id="username" type="text" className="form-control" placeholder="Enter username" />
                </div>

                <div className="form-group">
                    <label>Password</label>
                    <input value={this.state.password} onChange={this.onChangePassword} id="password" type="password" className="form-control" placeholder="Enter password" />
                </div>

                <div className="form-group">
                    <label>Full Name</label>
                    <input value={this.state.fullName} onChange={this.onChangeFullName} id="fullName" type="text" className="form-control" placeholder="Enter full name" />
                </div>

                <button type="submit" className="btn btn-dark btn-lg btn-block">Create account</button>
                <p className="forgot-password text-right">
                    Already have an <a href="/login">account</a>?
                </p>
            </form>
        );
    }
}