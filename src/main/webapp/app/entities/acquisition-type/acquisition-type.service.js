(function() {
    'use strict';
    angular
        .module('alexandriaApp')
        .factory('AcquisitionType', AcquisitionType);

    AcquisitionType.$inject = ['$resource'];

    function AcquisitionType ($resource) {
        var resourceUrl =  'api/acquisition-types/:id';

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
