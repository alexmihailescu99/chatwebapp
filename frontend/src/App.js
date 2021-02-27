import React from "react";
import './App.css';
import {
  BrowserRouter as Router,
  Route,
} from "react-router-dom";
import 'bootstrap/dist/css/bootstrap.min.css';
import axios from "axios";
import LoginPage from "./components/LoginPage";
import RegisterPage from "./components/RegisterPage";
import MainPage from "./components/MainPage";
export const backEndUrl = "http://localhost:8080/api";
class App extends React.Component{
  constructor() {
    super();
  }
  
  componentDidMount() {
    let token = (localStorage.getItem("jwtToken"));
    let options = {};
    if (token !== "null") {
      options = {
        headers: {
          "Authorization": "Bearer " + token
        }
      }
    }
    axios.get(backEndUrl + "/user/test", options)
      .then(res => {
        //alert(res.data);
      })
      .catch(err => {
        //alert(err.message);
        //alert(err.response.status)
        //alert(err.response)
        //if (err.response.status === 403) {
          localStorage.setItem("currentUser", "null");
          localStorage.setItem("jwtToken", "null");
          // window.location.href = "/login";
        //}
      })
  }
  render() {
    return (
      <Router>
        <Route exact path="/login" component={LoginPage} />
        <Route exact path="/register" component={RegisterPage} />
        <Route exact path="/" component={MainPage} />
      </Router>
    );
  }
  
}

export default App;
