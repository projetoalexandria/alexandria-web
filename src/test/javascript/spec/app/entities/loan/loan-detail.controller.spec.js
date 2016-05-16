'use strict';

describe('Controller Tests', function() {

    describe('Loan Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockLoan, MockStudent, MockBook;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockLoan = jasmine.createSpy('MockLoan');
            MockStudent = jasmine.createSpy('MockStudent');
            MockBook = jasmine.createSpy('MockBook');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Loan': MockLoan,
                'Student': MockStudent,
                'Book': MockBook
            };
            createController = function() {
                $injector.get('$controller')("LoanDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'alexandriaApp:loanUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
