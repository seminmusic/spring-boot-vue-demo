<template>
    <b-navbar toggleable="md" type="dark" variant="info">
        <b-navbar-brand :to="{ name: 'Home' }">Sema Cars</b-navbar-brand>
        <b-navbar-nav>
            <b-nav-item :to="{ name: 'CarList' }">Cars</b-nav-item>
        </b-navbar-nav>
    </b-navbar>
</template>
