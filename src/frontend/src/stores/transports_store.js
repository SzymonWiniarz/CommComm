import { defineStore } from "pinia";

export const useTransportsStore = defineStore("transports", {

    state: () => {
        return {
            transportsToSchool: [],
            transportsFromSchool: [],
            transportId: 1
        }
    },

    actions: {
        createTransportToSchool(transport) {
            const nextId = (this.transportId++).toString();
            transport.id = nextId;
            this.transportsToSchool.push(transport);
        },

        deleteTransportToSchool(id) {
            this.transportsToSchool = this.transportsToSchool.filter((transport) => transport.id != id);
        },

        getTransportToSchoolById(id) {
            const foundTransport = this.transportsToSchool.find((transport) => transport.id == id);

            if (foundTransport) {
                return Object.assign({}, foundTransport);
            }
        },

        getAllTransportsToSchool() {
            return this.transportsToSchool.map((transport) => Object.assign({}, transport));
        },

        updateTransportToSchool(transport) {
            this.deleteTransportToSchool(transport.id);
            this.transportsToSchool.push(transport);
        },

        createTransportFromSchool(transport) {
            const nextId = (this.transportId++).toString();
            transport.id = nextId;
            this.transportsFromSchool.push(transport);
        },

        deleteTransportFromSchool(id) {
            this.transportsFromSchool = this.transportsFromSchool.filter((transport) => transport.id != id);
        },

        getTransportFromSchoolById(id) {
            const foundTransport = this.transportsFromSchool.find((transport) => transport.id == id);

            if (foundTransport) {
                return Object.assign({}, foundTransport);
            }
        },

        getAllTransportsFromSchool() {
            return this.transportsFromSchool.map((transport) => Object.assign({}, transport));
        },

        updateTransportFromSchool(transport) {
            this.deleteTransportFromSchool(transport.id);
            this.transportsFromSchool.push(transport);
        }
    }

})