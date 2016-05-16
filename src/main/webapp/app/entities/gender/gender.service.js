(function() {
    'use strict';
    angular
        .module('alexandriaApp')
        .factory('Gender', Gender);

    Gender.$inject = ['$resource'];

    function Gender ($resource) {
        var resourceUrl =  'api/genders/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
