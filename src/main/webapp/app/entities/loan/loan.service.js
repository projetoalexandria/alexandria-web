(function() {
    'use strict';
    angular
        .module('alexandriaApp')
        .factory('Loan', Loan);

    Loan.$inject = ['$resource', 'DateUtils'];

    function Loan ($resource, DateUtils) {
        var resourceUrl =  'api/loans/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    data = angular.fromJson(data);
                    data.loanDate = DateUtils.convertLocalDateFromServer(data.loanDate);
                    data.devolutionDate = DateUtils.convertLocalDateFromServer(data.devolutionDate);
                    data.maxDevolutionDate = DateUtils.convertLocalDateFromServer(data.maxDevolutionDate);
                    return data;
                }
            },
            'update': {
                method: 'PUT',
                transformRequest: function (data) {
                    data.loanDate = DateUtils.convertLocalDateToServer(data.loanDate);
                    data.devolutionDate = DateUtils.convertLocalDateToServer(data.devolutionDate);
                    data.maxDevolutionDate = DateUtils.convertLocalDateToServer(data.maxDevolutionDate);
                    return angular.toJson(data);
                }
            },
            'save': {
                method: 'POST',
                transformRequest: function (data) {
                    data.loanDate = DateUtils.convertLocalDateToServer(data.loanDate);
                    data.devolutionDate = DateUtils.convertLocalDateToServer(data.devolutionDate);
                    data.maxDevolutionDate = DateUtils.convertLocalDateToServer(data.maxDevolutionDate);
                    return angular.toJson(data);
                }
            }
        });
    }
})();
