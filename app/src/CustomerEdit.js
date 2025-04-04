import React, { useEffect, useState } from 'react';
import { Link, useNavigate, useParams } from 'react-router-dom';
import { Button, Container, Form, FormGroup, Input, Label } from 'reactstrap';
import AppNavbar from './AppNavbar';
import { useCookies } from 'react-cookie';

const CustomerEdit = () => {
    const initialFormState = {
        customerId: '',
        firstName: '',
        lastName: '',
        address: [{
            street: '',
            city: ''
        }]
    };
    const [customer, setCustomer] = useState(initialFormState);
    const navigate = useNavigate();
    const { id } = useParams();
    const [cookies] = useCookies(['XSRF-TOKEN']);

    useEffect(() => {
        if (id !== 'new') {
            fetch(`/customer/${id}`)
                .then(response => response.json())
                .then(data => setCustomer(data));
        }
    }, [id, setCustomer]);

    const handleChange = (event) => {
        const { name, value } = event.target

        //Only currently supports one address:
        if (name === 'street') {
            customer.address[0].street = value;
        } else if (name === 'city') {
            customer.address[0].city = value;
        }

        setCustomer({ ...customer, [name]: value })
    }

    const handleSubmit = async (event) => {
        event.preventDefault();

        await fetch("/customer", {
            method: (id !== 'new') ? 'PUT' : 'POST',
            headers: {
                'X-XSRF-TOKEN': cookies['XSRF-TOKEN'],
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(customer),
            credentials: 'include'
        });
        setCustomer(initialFormState);
        navigate('/customers');
    }

    const title = <h2>{id !== 'new' ? 'Edit Customer' : 'Add Customer'}</h2>;

    return (<div>
            <AppNavbar/>
            <Container>
                {title}
                <Form onSubmit={handleSubmit}>
                    <FormGroup>
                        <Label for="customerId">Customer ID</Label>
                        <Input type="text" name="customerId" id="customerId" value={customer.customerId || ''}
                               onChange={handleChange} autoComplete="customerId" disabled={id !== 'new'} />
                    </FormGroup>
                    <FormGroup>
                        <Label for="firstName">First Name</Label>
                        <Input type="text" name="firstName" id="firstName" value={customer.firstName || ''}
                               onChange={handleChange} autoComplete="firstName"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="lastName">Last Name</Label>
                        <Input type="text" name="lastName" id="lastName" value={customer.lastName || ''}
                               onChange={handleChange} autoComplete="lastName"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="street">Street</Label>
                        <Input type="text" name="street" id="street" defaultValue={customer.address[0].street || ''}
                               onChange={handleChange} autoComplete="address-level1"/>
                    </FormGroup>
                    <FormGroup>
                        <Label for="city">City</Label>
                        <Input type="text" name="city" id="city" defaultValue={customer.address[0].city || ''}
                               onChange={handleChange} autoComplete="address-level1"/>
                    </FormGroup>
                    <FormGroup>
                        <Button color="primary" type="submit">Save</Button>{' '}
                        <Button color="secondary" tag={Link} to="/customers">Cancel</Button>
                    </FormGroup>
                </Form>
            </Container>
        </div>
    )
};

export default CustomerEdit;