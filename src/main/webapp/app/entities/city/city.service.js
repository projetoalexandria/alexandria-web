(function() {
    'use strict';
    angular
        .module('alexandriaApp')
        .factory('City', City);

    City.$inject = ['$resource'];

    function City ($resource) {
        var resourceUrl =  'api/cities/:id';

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
