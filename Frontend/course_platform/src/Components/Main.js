import { Route, Routes, Navigate } from "react-router-dom";
import Navbar from "./Navbar";
import Home from "./Home";
import Courses from "./Courses";

import "../Styles/Main.css"


const Main = () => {

    return (
        <>
            <Navbar />
            <Routes>
                <Route index element={<Home />} />
{/* <Route path="/cos tam " element={<jakis tam komponent />} /> */}
{/* <Route path="/" element={<Home />} /> */}
                <Route path="/" element={<Home />} />
                <Route path="/courses" element={<Courses />} />
                <Route path="*" element={<Navigate to="/" />} />
            </Routes>
        </>
    );
};

export default Main;