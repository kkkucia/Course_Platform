import { Route, Routes, Navigate } from "react-router-dom";
import Navbar from "./Navbar";
import Home from "./Home";
import AvailableCoursesList from "./AvailableCoursesList";

import "../Styles/Main.css"
import AddCourses from "./AddCourse";


const Main = () => {

    return (
        <>
            <Navbar />
            <Routes>
                <Route index element={<Home />} />
{/* <Route path="/cos tam " element={<jakis tam komponent />} /> */}
{/* <Route path="/" element={<Home />} /> */}
                <Route path="/" element={<Home />} />
                <Route path="/courses/available" element={<AvailableCoursesList />} />
                <Route path="/courses/add" element={<AddCourses />} />
                <Route path="*" element={<Navigate to="/" />} />
            </Routes>
        </>
    );
};

export default Main;