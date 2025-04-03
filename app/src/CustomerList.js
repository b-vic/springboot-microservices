import React, { useEffect, useState } from 'react';
import { Button, ButtonGroup, Container, Table } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { Link } from 'react-router-dom';

const CustomerList = () => {

    const [customers, setCustomers] = useState([]);
    const [loading, setLoading] = useState(false);

    useEffect(() => {
        setLoading(true);

        fetch('customer')
            .then(response => response.json())
            .then(data => {
                setCustomers(data);
                setLoading(false);
            })
    }, []);

    if (loading) {
        return <p>Loading...</p>;
    }

    const customerList = customers.map(customer => {
        return <tr key={customer.customerId}>
            <td style={{whiteSpace: 'nowrap'}}>{customer.firstName} {customer.lastName}</td>
            <td>
                <ButtonGroup>
                    <Button size="sm" color="primary" tag={Link} to={"/customer"}>Edit</Button>
                </ButtonGroup>
            </td>
        </tr>
    });

    return (
        <div>
            <AppNavbar/>
            <Container fluid>
                <div className="float-end">
                    <Button color="success" tag={Link} to="/customer">Add Customer</Button>
                </div>
                <h3>Customers</h3>
                <Table className="mt-4">
                    <thead>
                    <tr>
                        <th width="30%">Name</th>
                        <th width="30%">Action</th>
                    </tr>
                    </thead>
                    <tbody>
                    {customerList}
                    </tbody>
                </Table>
            </Container>
        </div>
    );
};

export default CustomerList;