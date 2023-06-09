import { Route, Routes, Navigate } from "react-router-dom";
import Navbar from "./Navbar";
import Home from "./Home";
import AvailableCoursesList from "./Pages/AvailableCoursesList";

import "../Styles/Main.css"
import AddCourses from "./Pages/AddCourse";
import GeneralList from "./Pages/GeneralList";


const Main = () => {

    return (
        <>
            <Navbar />
            <Routes>
                <Route index element={<Home />} />
                <Route path="/" element={<Home />} />
                <Route path="/basic/courses" element={<GeneralList srcLink={'http://localhost:8080/courses'} text={"List of all courses"} />} />
                <Route path="/basic/categories" element={<GeneralList srcLink={'http://localhost:8080/categories'} text={"List of all categories"} />} />
                <Route path="/basic/reservations" element={<GeneralList srcLink={'http://localhost:8080/reservations'} text={"List of all reservations"} />} />
                <Route path="/views/availableCourses" element={<AvailableCoursesList />} />
                <Route path="/views/invoices" element={<GeneralList srcLink={'http://localhost:8080/invoices'} text={"List of invoices"} />} />
                <Route path="/procedures/addCourse" element={<AddCourses />} />
                
                <Route path="*" element={<Navigate to="/" />} />
            </Routes>
        </>
    );
};

export default Main;