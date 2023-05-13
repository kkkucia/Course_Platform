import './Styles/App.css';
import { BrowserRouter as Router } from "react-router-dom";
import Main from "./Components/Main";


const App = () => {

  return (
    <div className="App">
      <Router>
        <Main />
      </Router>
    </div>
  );
}

export default App;