import React from 'react';
import './App.css';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';
import { Button, Container } from 'reactstrap';

const Home = () => {
    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <table>
                    <tr>
                        <td>
                            <img src="logo192.png" width="20px" height="20px"/>
                        </td>
                        <td>
                            <Button color="link"><Link to="/customers">Manage Customers</Link></Button>
                        </td>
                    </tr>
                    <tr>
                        <td>
                            <img src="logo192.png" width="20px" height="20px"/>
                        </td>
                        <td>
                            <Button color="link"><Link to="/products">Manage Products</Link></Button>
                        </td>
                    </tr>
                </table>
            </Container>
        </div>
    );
}

export default Home;